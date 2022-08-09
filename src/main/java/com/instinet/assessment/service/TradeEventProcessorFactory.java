package com.instinet.assessment.service;

import com.instinet.assessment.domain.Trade;
import com.instinet.assessment.util.eventbus.WithEventBusSubscriber;
import com.instinet.assessment.util.processor.CountingFlagTradeProcessor;
import com.instinet.assessment.util.processor.LargestTradeProcessor;
import com.instinet.assessment.util.processor.VWAPTradeProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TradeEventProcessorFactory {

    private static final Logger LOGGER = LogManager.getLogger(TradeEventProcessorFactory.class);

    private final WithEventBusSubscriber<Trade> eventBusSubscriber;

    public TradeEventProcessorFactory(WithEventBusSubscriber<Trade> eventBusSubscriber) {
        this.eventBusSubscriber = eventBusSubscriber;
    }

    public void addCountingFlagSubscriber(String symbol, String flag) {
        eventBusSubscriber.addSubscriber(
                new CountingFlagTradeProcessor(symbol, flag,
                        count -> LOGGER.info("Count of trades with symbol:{}, flag:{} => {}", symbol, flag, count)));
    }

    public void addLargestTradeSubscriber(String symbol) {
        eventBusSubscriber.addSubscriber(
                new LargestTradeProcessor(symbol,
                        size -> LOGGER.info("Largest Trade for {} found => {}", symbol, size))
        );
    }

    public void addVWAPSubscriber(String symbol) {
        eventBusSubscriber.addSubscriber(
                new VWAPTradeProcessor(symbol,
                        price -> LOGGER.info("Current VWAP for {} is => {}", symbol, price))
        );
    }

}
