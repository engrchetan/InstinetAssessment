package com.instinet.assessment;

import com.instinet.assessment.domain.Trade;
import com.instinet.assessment.service.TradeEventProcessorFactory;
import com.instinet.assessment.service.TradeEventPublisher;

import java.util.Arrays;
import java.util.List;

/**
 * Entry point to Trade Processing orchestrating different services
 */
public class TradeProcessingSimulator {

    public static final String SYMBOL_XYZ_LN = "XYZ LN";
    public static final String SYMBOL_123_LN = "123 LN";
    public static final int TRADE_REPLAY_WAIT_SECS = 2;

    public static void main(String[] args) throws InterruptedException {
        TradeProcessingApplication application = new TradeProcessingApplication();

        TradeEventProcessorFactory processor = application.getProcessor();
        // Views to build
        processor.addLargestTradeSubscriber(SYMBOL_XYZ_LN);
        processor.addVWAPSubscriber(SYMBOL_123_LN);
        processor.addCountingFlagSubscriber(SYMBOL_123_LN, "X");

        // Play Sample Data given in problem
        runSampleTrades(application.getPublisher(), sampleTrades());

        // Play Additional Sample Data to test logic
        runSampleTrades(application.getPublisher(), sampleTrades2());

        Thread.currentThread().join(); // Wait for other threads to finish
    }

    private static void runSampleTrades(TradeEventPublisher publisher, List<Trade> trades) throws InterruptedException {
        trades.forEach( t -> {
                    publisher.processTrade(t);
            try {
                Thread.sleep(TRADE_REPLAY_WAIT_SECS * 1000); // Slow down trade events for better visibility
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static List<Trade> sampleTrades() {
        return Arrays.asList(
                new Trade(SYMBOL_XYZ_LN, 1481107485791l, 200, 1000).addFlag("A"),
                new Trade(SYMBOL_XYZ_LN, 1481107485791l, 200.01, 968).addFlag("Z"),
                new Trade(SYMBOL_123_LN, 1481107485791l, 98.6, 746).addFlag("X")
        );
    }

    // Additional scenarios to test VWAP, Incremental Max & count for flag
    public static List<Trade> sampleTrades2() {
        return Arrays.asList(
                new Trade(SYMBOL_XYZ_LN, 1481107485791l, 200, 100).addFlag("A"),
                new Trade(SYMBOL_XYZ_LN, 1481107485791l, 200.01, 9680).addFlag("Z"),
                new Trade(SYMBOL_123_LN, 1481107485791l, 99, 500).addFlag("X"),
                new Trade(SYMBOL_123_LN, 1481107485791l, 98, 1000).addFlag("X")
        );
    }

}
