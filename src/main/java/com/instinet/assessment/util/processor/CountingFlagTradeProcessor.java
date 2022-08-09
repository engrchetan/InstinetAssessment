package com.instinet.assessment.util.processor;

import com.instinet.assessment.domain.Trade;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CountingFlagTradeProcessor implements WithTradeSymbolFilter {

    private final String symbol;
    private final String flag;
    private final Consumer<Long> processingResultHandler;
    private AtomicLong result = new AtomicLong();
    public CountingFlagTradeProcessor(String symbol, String flag, Consumer<Long> processingResultHandler) {
        this.symbol = symbol;
        this.flag = flag;
        this.processingResultHandler = processingResultHandler;
    }

    @Override
    public String symbolToFilter() {
        return symbol;
    }

    @Override
    public void acceptFilteredStream(Stream<Trade> tradeStream) {
        tradeStream.filter(t -> t.getFlags().contains(flag))
                .forEach(t -> processingResultHandler.accept(result.incrementAndGet()));
    }
}
