package com.instinet.assessment.util.eventbus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Mock EventBus middleware which could be replaced with Kafka or other messaging layer
 */
public class EventBusImpl<T extends EventBusEntity> implements WithEventBusPublisher<T>, WithEventBusSubscriber<T> {

    private static final Logger LOGGER = LogManager.getLogger(EventBusImpl.class);
    private final List<T> masterEventStore = new LinkedList<>();
    // Allow each subscriber to consume events independently
    private final List<BlockingQueue<T>> eventStoreBySubscriber = new CopyOnWriteArrayList<>();
    private final Supplier<ExecutorService> executorServiceSupplier;

    public EventBusImpl() {
        this(() -> Executors.newSingleThreadExecutor()); // Default create new executor thread
    }
    public EventBusImpl(Supplier<ExecutorService> executorServiceSupplier) {
        this.executorServiceSupplier = executorServiceSupplier; // Allow testing with custom supplier
    }

    @Override
    public void publish(T event) {
        LOGGER.info("Publishing event {}", event);
        synchronized(masterEventStore) {
            masterEventStore.add(event);
        }
        notifySubscriberQueue(event);
    }

    private void notifySubscriberQueue(T event) {
        LOGGER.info("Notified {} subscribers", eventStoreBySubscriber.size());
        eventStoreBySubscriber.forEach(q -> q.offer(event));
    }

    @Override
    public void addSubscriber(Consumer<Stream<T>> subscriber) {
        BlockingQueue<T> subscriberQueue = generateQueue(subscriber);
        runEventProcessingStream(subscriber, subscriberQueue);
    }

    private void runEventProcessingStream(Consumer<Stream<T>> subscriber, BlockingQueue<T> subscriberQueue) {
        String subscriberType = subscriber.getClass().getSimpleName();
        executorServiceSupplier.get().submit(() -> {
            Stream<T> generate = Stream.generate(() -> {
                try {
                    T trade = subscriberQueue.take();
                    LOGGER.debug("Supplying {} with {}", subscriberType, trade);
                    return trade;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            // Pass stream to subscriber to add event consume logic
            subscriber.accept(generate);
        });
    }

    private BlockingQueue<T> generateQueue(Consumer<Stream<T>> subscriber) {
        BlockingQueue<T> subscriberQueue;
        synchronized(masterEventStore) {
            subscriberQueue = new LinkedBlockingQueue<>(masterEventStore);
        }
        eventStoreBySubscriber.add(subscriberQueue);
        return subscriberQueue;
    }
}

