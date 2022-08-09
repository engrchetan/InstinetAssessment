package com.instinet.assessment.domain;

import com.instinet.assessment.util.eventbus.EventBusEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Trade implements EventBusEntity {
    private final String symbol;
    private final long timestamp;
    private final double price;
    private final long size;
    private final Set<String> flags = new HashSet<>();

    public Trade(String symbol, long timestamp, double price, long size) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.price = price;
        this.size = size;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }

    public long getSize() {
        return size;
    }

    public Set<String> getFlags() {
        return flags;
    }

    public Trade addFlag(String flag) {
        getFlags().add(flag);
        return this;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "symbol='" + symbol + '\'' +
                ", timestamp=" + timestamp +
                ", price=" + price +
                ", size=" + size +
                ", flags=" + flags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return timestamp == trade.timestamp && Double.compare(trade.price, price) == 0 && size == trade.size && Objects.equals(symbol, trade.symbol) && Objects.equals(flags, trade.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, timestamp, price, size, flags);
    }
}
