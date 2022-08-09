package com.instinet.assessment.util.processor;

import com.instinet.assessment.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.instinet.assessment.util.processor.TestDataUtil.*;
import static com.instinet.assessment.util.processor.TestDataUtil.SYM;
import static org.junit.jupiter.api.Assertions.*;

public class VWAPTradeProcessorTest {
    public static final double DELTA = 0.001;
    private VWAPTradeProcessor processor;
    private List<Double> resultUpdates;

    @BeforeEach
    void setUp() {
        resultUpdates = new LinkedList();
        processor = new VWAPTradeProcessor(SYM, resultUpdates::add);
    }

    @Test
    public void testNonMatchingSymbolHasNoResult() {
        processor.accept(noMatchingSymbolTestTradeScenario());

        assertEquals(0, resultUpdates.size(), "No Result Update");
    }

    @Test
    public void testMultipleUpdatesDifferentPrice() {
        Stream<Trade> tradeStream = testTrades(
                new Trade(SYM, 1, 99, 10),
                new Trade(SYM, 1, 100, 1000),
                new Trade(SYM, 1, 99, 990) // Equal size of two price values
        );
        processor.accept(tradeStream);

        assertEquals(3, resultUpdates.size(), "Result Updates");
        assertEquals(99, resultUpdates.get(0), DELTA, "First Result");
        assertEquals(99.99, resultUpdates.get(1), DELTA, "First Result");
        assertEquals(99.5, resultUpdates.get(2), DELTA, "First Result");
    }

}