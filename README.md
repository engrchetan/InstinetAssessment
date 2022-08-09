# Design
* EventBusImpl is mock EventBus implementation to asynchronously update subscribers
  * Each subscriber has its own event queue replicated & processing in its own thread
* Use Java 8 Streams to reuse filter / foreach functions
* Stream.generate() allows infinite stream
* Functional processors for required logic added in processor package
* TradeEventPublisher has basic validations 

# How To Run Simulator
* Execute TradeProcessingSimulator

# OUTPUT

03:32:13.819 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='XYZ LN', timestamp=1481107485791, price=200.0, size=1000, flags=[A]}
03:32:13.820 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:13.821 [pool-2-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Largest Trade for XYZ LN found => 1000
03:32:15.821 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='XYZ LN', timestamp=1481107485791, price=200.01, size=968, flags=[Z]}
03:32:15.821 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:17.822 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='123 LN', timestamp=1481107485791, price=98.6, size=746, flags=[X]}
03:32:17.822 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:17.823 [pool-4-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Count of trades with symbol:123 LN, flag:X => 1
03:32:17.824 [pool-3-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Current VWAP for 123 LN is => 98.6
03:32:19.823 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='XYZ LN', timestamp=1481107485791, price=200.0, size=100, flags=[A]}
03:32:19.824 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:21.825 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='XYZ LN', timestamp=1481107485791, price=200.01, size=9680, flags=[Z]}
03:32:21.825 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:21.825 [pool-2-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Largest Trade for XYZ LN found => 9680
03:32:23.825 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='123 LN', timestamp=1481107485791, price=99.0, size=500, flags=[X]}
03:32:23.826 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:23.827 [pool-4-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Count of trades with symbol:123 LN, flag:X => 2
03:32:23.827 [pool-3-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Current VWAP for 123 LN is => 98.7605136436597
03:32:25.827 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Publishing event Trade{symbol='123 LN', timestamp=1481107485791, price=98.0, size=1000, flags=[X]}
03:32:25.828 [main] INFO  com.instinet.assessment.util.eventbus.EventBusImpl - Notified 3 subscribers
03:32:25.828 [pool-4-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Count of trades with symbol:123 LN, flag:X => 3
03:32:25.828 [pool-3-thread-1] INFO  com.instinet.assessment.service.TradeEventProcessorFactory - Current VWAP for 123 LN is => 98.42190560997328
