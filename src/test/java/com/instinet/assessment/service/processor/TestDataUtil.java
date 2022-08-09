package com.instinet.assessment.service.processor;

import com.instinet.assessment.domain.Trade;

import java.util.Arrays;
import java.util.stream.Stream;

public class TestDataUtil {
    public static final String SYM = "ABC.L";
    public static final String FLAG = "X";

    public static Stream<Trade> testTrades(Trade... trades) {
        return Arrays.stream(trades);
    }

    public static Stream<Trade> noMatchingSymbolTestTradeScenario() {
        return testTrades(
                new Trade("UNKNOWN", 1, 100, 1000),
                new Trade(SYM.toLowerCase(), 1, 100, 1000)
        );
    }
}
