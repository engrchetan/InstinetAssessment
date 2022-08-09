package com.instinet.assessment.util.processor;

import com.instinet.assessment.domain.Trade;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface WithTradeSymbolFilter extends Consumer<Stream<Trade>> {

    String symbolToFilter();

    void acceptFilteredStream(Stream<Trade> tradeStream);

    @Override
    default void accept(Stream<Trade> tradeStream) {
        acceptFilteredStream(tradeStream.filter(t -> symbolToFilter().equals(t.getSymbol())));
    }

}
