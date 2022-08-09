package com.instinet.assessment.service;

import com.instinet.assessment.domain.Trade;
import com.instinet.assessment.util.eventbus.WithEventBusPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.instinet.assessment.service.processor.TestDataUtil.SYM;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TradeEventPublisherTest {

    private TradeEventPublisher tradeEventPublisher;
    private WithEventBusPublisher<Trade> eventBusPublisher;

    @BeforeEach
    void setUp() {
        eventBusPublisher = mock(WithEventBusPublisher.class);
        tradeEventPublisher = new TradeEventPublisher(eventBusPublisher);
    }

    @Test
    void testValidTradePublication() {
        Trade tradeEvent = new Trade(SYM, 1l, 100, 1000);
        tradeEventPublisher.processTrade(tradeEvent);

        verify(eventBusPublisher, atLeastOnce()).publish(eq(tradeEvent));
    }

    @Test
    void testInvalidTradeResultsInException() {
        final Trade invalidTrade = new Trade(null, 1l, 100, 1000);
        assertThrows(RuntimeException.class,
                () -> tradeEventPublisher.processTrade(invalidTrade));

        final Trade invalidTrade1 = new Trade(SYM, -11l, 100, 1000);
        assertThrows(RuntimeException.class,
                () -> tradeEventPublisher.processTrade(invalidTrade1));

        final Trade invalidTrade2 = new Trade(SYM, 1l, -20, 1000);
        assertThrows(RuntimeException.class,
                () -> tradeEventPublisher.processTrade(invalidTrade2));

        final Trade invalidTrade3 = new Trade(SYM, 1l, 100, -3000);
        assertThrows(RuntimeException.class,
                () -> tradeEventPublisher.processTrade(invalidTrade3));

        verifyNoInteractions(eventBusPublisher);
    }
}