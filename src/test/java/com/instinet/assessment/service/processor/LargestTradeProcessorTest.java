package com.instinet.assessment.service.processor;

import com.instinet.assessment.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static com.instinet.assessment.service.processor.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class LargestTradeProcessorTest {
    private LargestTradeProcessor processor;
    private List<Long> resultUpdates;

    @BeforeEach
    void setUp() {
        resultUpdates = new LinkedList();
        processor = new LargestTradeProcessor(SYM, resultUpdates::add);
    }

    @Test
    public void testNonMatchingSymbolHasNoResult() {
        processor.accept(noMatchingSymbolTestTradeScenario());

        assertEquals(0, resultUpdates.size(), "No Result Update");
    }

    @Test
    public void testIncrementalSizeMultipleResult() {
        Stream<Trade> tradeStream = testTrades(
                new Trade(SYM, 1, 100, 10),
                new Trade(SYM, 1, 100, 1000),
                new Trade(SYM, 1, 100, 100), // Smaller ignored
                new Trade(SYM, 1, 100, 10000)
        );
        processor.accept(tradeStream);

        assertEquals(3, resultUpdates.size(), "Result Updates");
        assertEquals(10, resultUpdates.get(0), "First Result");
        assertEquals(1000, resultUpdates.get(1), "First Result");
        assertEquals(10000, resultUpdates.get(2), "First Result");
    }

}