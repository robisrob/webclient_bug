# Example problem webclient Spring
## Purpose
Demonstrate problem with Spring Webclient. This can be reproduced by running the [WebClientTest](src/test/java/com/github/robisrob/webclient_bug/WebClientTest.java). This error is only visible in the console, not in the error returned by the Mono.

## Problem
When doing a exponential backoff on a mono that contains an error, there is an unexpected exception printed in the console:

```
reactor.core.Exceptions$BubblingException: java.lang.IllegalStateException: Only one connection receive subscriber allowed.
	at reactor.core.Exceptions.bubble(Exceptions.java:154) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators.onErrorDropped(Operators.java:512) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onError(FluxMap.java:120) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onError(FluxPeek.java:214) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onError(FluxMap.java:126) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators.error(Operators.java:181) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.netty.channel.FluxReceive.startReceiver(FluxReceive.java:277) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.FluxReceive.subscribe(FluxReceive.java:127) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.core.publisher.FluxMap.subscribe(FluxMap.java:62) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.netty.ByteBufFlux.subscribe(ByteBufFlux.java:290) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.core.publisher.FluxPeek.subscribe(FluxPeek.java:83) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap.subscribe(FluxMap.java:62) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap.subscribe(FluxMap.java:62) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume.subscribe(FluxOnErrorResume.java:47) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreElements.subscribe(MonoIgnoreElements.java:37) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:3608) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:172) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:3608) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:172) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:3608) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:97) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoFlatMap$FlatMapMain.secondError(MonoFlatMap.java:185) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoFlatMap$FlatMapInner.onError(MonoFlatMap.java:251) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators$MonoSubscriber.onError(Operators.java:1521) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreInner.onError(MonoIgnoreThen.java:235) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:100) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators.error(Operators.java:181) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoError.subscribe(MonoError.java:52) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:3608) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:97) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators$MonoSubscriber.onError(Operators.java:1521) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreInner.onError(MonoIgnoreThen.java:235) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreElements$IgnoreElementsSubscriber.onError(MonoIgnoreElements.java:76) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:100) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators.error(Operators.java:181) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxError.subscribe(FluxError.java:43) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Flux.subscribe(Flux.java:7734) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:97) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onError(FluxMap.java:126) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onError(FluxMap.java:126) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators.error(Operators.java:181) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onSubscribe(FluxPeek.java:157) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onSubscribe(FluxMap.java:86) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators.error(Operators.java:180) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.netty.channel.FluxReceive.startReceiver(FluxReceive.java:277) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.FluxReceive.subscribe(FluxReceive.java:127) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.core.publisher.FluxMap.subscribe(FluxMap.java:62) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.netty.ByteBufFlux.subscribe(ByteBufFlux.java:290) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.core.publisher.FluxPeek.subscribe(FluxPeek.java:83) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap.subscribe(FluxMap.java:62) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap.subscribe(FluxMap.java:62) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxOnErrorResume.subscribe(FluxOnErrorResume.java:47) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreElements.subscribe(MonoIgnoreElements.java:37) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:3608) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:172) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Mono.subscribe(Mono.java:3608) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:172) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:150) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:114) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1476) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxDefaultIfEmpty$DefaultIfEmptySubscriber.onComplete(FluxDefaultIfEmpty.java:100) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onComplete(FluxMapFuseable.java:144) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxContextStart$ContextStartSubscriber.onComplete(FluxContextStart.java:122) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onComplete(FluxMapFuseable.java:336) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableConditionalSubscriber.onComplete(FluxFilterFuseable.java:385) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1479) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.MonoCollectList$MonoBufferAllSubscriber.onComplete(MonoCollectList.java:118) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onComplete(FluxMap.java:136) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onComplete(FluxPeek.java:252) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onComplete(FluxMap.java:136) ~[reactor-core-3.2.3.RELEASE.jar:3.2.3.RELEASE]
	at reactor.netty.channel.FluxReceive.terminateReceiver(FluxReceive.java:378) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:202) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.FluxReceive.onInboundComplete(FluxReceive.java:343) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.ChannelOperations.onInboundComplete(ChannelOperations.java:325) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.ChannelOperations.terminate(ChannelOperations.java:372) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.http.client.HttpClientOperations.onInboundNext(HttpClientOperations.java:511) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at reactor.netty.channel.ChannelOperationsHandler.channelRead(ChannelOperationsHandler.java:141) ~[reactor-netty-0.8.3.RELEASE.jar:0.8.3.RELEASE]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:102) ~[netty-codec-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:438) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:323) ~[netty-codec-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:297) ~[netty-codec-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:253) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1434) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:965) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:648) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:583) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:500) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:462) ~[netty-transport-4.1.31.Final.jar:4.1.31.Final]
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:897) ~[netty-common-4.1.31.Final.jar:4.1.31.Final]
	at java.base/java.lang.Thread.run(Thread.java:834) ~[na:na]
Caused by: java.lang.IllegalStateException: Only one connection receive subscriber allowed.
	... 102 common frames omitted
```