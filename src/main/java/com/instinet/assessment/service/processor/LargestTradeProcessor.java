package com.instinet.assessment.service.processor;

import com.instinet.assessment.domain.Trade;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class LargestTradeProcessor implements WithTradeSymbolFilter {

    private final String symbol;
    private final Consumer<Long> processingResultHandler;
    private AtomicLong largest = new AtomicLong();
    public LargestTradeProcessor(String symbol, Consumer<Long> processingResultHandler) {
        this.symbol = symbol;
        this.processingResultHandler = processingResultHandler;
    }

    @Override
    public String symbolToFilter() {
        return symbol;
    }

    @Override
    public void acceptFilteredStream(Stream<Trade> tradeStream) {
        tradeStream.forEach(t -> {
                    long size = t.getSize();
                    if(largest.get() < size) {
                        largest.set(size);
                        processingResultHandler.accept(size);
                    }
                });
    }

}
