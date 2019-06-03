package com.zerses.example;

import org.apache.camel.builder.RouteBuilder;

public class ExampleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        SampleSuspensionProcessor routeSuspensionProcessor = new SampleSuspensionProcessor(getContext(), "foo");

        from("timer://timer01?fixedRate=true&period=1000")
                .routeId("timeRoute")
                .process(routeSuspensionProcessor)
                .end();


        from("timer://foo?fixedRate=true&period=500")
                .routeId("foo")
                .log("FOO");
    }
}
