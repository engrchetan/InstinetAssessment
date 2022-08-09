package com.instinet.assessment.service.processor;

import com.instinet.assessment.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CountingFlagTradeProcessorTest {

    private CountingFlagTradeProcessor tradeProcessor;
    private List<Long> resultUpdates;

    @BeforeEach
    void setUp() {
        resultUpdates = new LinkedList();
        tradeProcessor = new CountingFlagTradeProcessor(TestDataUtil.SYM, TestDataUtil.FLAG, resultUpdates::add);
    }

    @Test
    public void testNonMatchingSymbolHasNoResult() {
        tradeProcessor.accept(TestDataUtil.noMatchingSymbolTestTradeScenario());
        assertEquals(0, resultUpdates.size(), "No Result Update");
    }

    @Test
    public void testNonMatchingFlagHasNoResult() {
        tradeProcessor.accept(TestDataUtil.testTrades(
                new Trade(TestDataUtil.SYM, 1l, 100, 1000).addFlag("UNKNOWN")
        ));
        assertEquals(0, resultUpdates.size(), "No Result Update");
    }

    @Test
    public void testMatchingFlagResult() {
        tradeProcessor.accept(TestDataUtil.testTrades(
                new Trade(TestDataUtil.SYM, 1l, 100, 1000).addFlag(TestDataUtil.FLAG),
                new Trade(TestDataUtil.SYM, 2l, 99, 1000).addFlag(TestDataUtil.FLAG),
                new Trade(TestDataUtil.SYM, 3l, 98, 1000).addFlag(TestDataUtil.FLAG)
        ));
        assertEquals(3, resultUpdates.size(), "Result Updates");
        assertEquals(1, resultUpdates.get(0), "First Result");
        assertEquals(2, resultUpdates.get(1), "Second Result");
        assertEquals(3, resultUpdates.get(2), "Third Result");
    }
}