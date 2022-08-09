package com.instinet.assessment.util.eventbus;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface WithEventBusSubscriber<T extends EventBusEntity> {

    void addSubscriber(Consumer<Stream<T>> subscriber);

}
