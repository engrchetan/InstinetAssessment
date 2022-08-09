package com.instinet.assessment.service.processor;

import com.instinet.assessment.domain.Trade;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class VWAPTradeProcessor implements WithTradeSymbolFilter {

    private final String symbol;
    private final Consumer<Double> processingResultHandler;
    private volatile VWAPCalc current = new VWAPCalc();

    public VWAPTradeProcessor(String symbol, Consumer<Double> processingResultHandler) {
        this.symbol = symbol;
        this.processingResultHandler = processingResultHandler;
    }

    @Override
    public String symbolToFilter() {
        return symbol;
    }

    @Override
    public void acceptFilteredStream(Stream<Trade> tradeStream) {
        // Trade -> Nth VWAP numerator & denominator -> Aggregated VWAP -> numerator / denominator
        tradeStream.map(VWAPCalc::new)
                   .forEach(this::updateVWAP);
    }

    private void updateVWAP(VWAPCalc vwapCalc) {
        current.priceSizeProduct += vwapCalc.priceSizeProduct;
        current.sizeSum += vwapCalc.sizeSum;
        double vwap = current.priceSizeProduct / current.sizeSum;
        processingResultHandler.accept(vwap);
    }

    private static final class VWAPCalc {
        public double priceSizeProduct;
        public double sizeSum;

        private VWAPCalc() {}

        private VWAPCalc(Trade trade) {
            priceSizeProduct = trade.getPrice() * trade.getSize();
            sizeSum = trade.getSize();
        }
    }
}
