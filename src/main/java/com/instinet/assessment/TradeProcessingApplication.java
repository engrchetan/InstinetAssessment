package com.instinet.assessment;

import com.instinet.assessment.domain.Trade;
import com.instinet.assessment.service.TradeEventProcessorFactory;
import com.instinet.assessment.service.TradeEventPublisher;
import com.instinet.assessment.util.eventbus.EventBusImpl;

public class TradeProcessingApplication {
    private final EventBusImpl<Trade> tradeEventBus;
    private final TradeEventPublisher publisher;
    private final TradeEventProcessorFactory processor;

    public TradeProcessingApplication() {
        tradeEventBus = new EventBusImpl<>();
        publisher = new TradeEventPublisher(tradeEventBus);
        processor = new TradeEventProcessorFactory(tradeEventBus);
    }

    public TradeEventPublisher getPublisher() {
        return publisher;
    }

    public TradeEventProcessorFactory getProcessor() {
        return processor;
    }
}
