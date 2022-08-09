package com.instinet.assessment.util.eventbus;

public interface WithEventBusPublisher<T extends EventBusEntity> {

    void publish(T event);
}
