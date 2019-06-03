package com.zerses.example;

import org.apache.camel.CamelContext;

public class SampleSuspensionProcessor extends AbstractRouteSuspensionProcessor {


    public SampleSuspensionProcessor(CamelContext camelContext, String routeIdToControl) {
        super(camelContext, routeIdToControl);
    }


    @Override
    protected boolean isServiceUp() {
        counter++;
        return ((counter % 10) < 5);
    }
}
