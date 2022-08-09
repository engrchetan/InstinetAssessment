package com.instinet.assessment.service;

import com.instinet.assessment.domain.Trade;
import com.instinet.assessment.util.eventbus.WithEventBusPublisher;

import java.util.Objects;

public class TradeEventPublisher {

    private final WithEventBusPublisher<Trade> tradePublisher;

    public TradeEventPublisher(WithEventBusPublisher<Trade> tradePublisher) {
        this.tradePublisher = tradePublisher;
    }

    public void processTrade(Trade tradeEvent) {
        validateTrade(tradeEvent);
        tradePublisher.publish(tradeEvent);
    }

    private void validateTrade(Trade tradeEvent) {
        Objects.requireNonNull(tradeEvent, "TradeEvent null");
        if(tradeEvent.getTimestamp() <= 0) {
            raiseFieldValidationException("timestamp", tradeEvent);
        }
        if(!Double.isFinite(tradeEvent.getPrice()) || tradeEvent.getPrice() <= 0) {
            raiseFieldValidationException("price", tradeEvent);
        }
        if(tradeEvent.getSize() <= 0) {
            raiseFieldValidationException("size", tradeEvent);
        }
        if(tradeEvent.getSymbol() == null || "".equals(tradeEvent.getSymbol().trim())) {
            raiseFieldValidationException("symbol", tradeEvent);
        }
    }

    private static void raiseFieldValidationException(String field, Trade tradeEvent) {
        throw new RuntimeException("Invalid " + field + " on TradeEvent " + tradeEvent);
    }
}
