package com.zerses.example;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

abstract public class AbstractRouteSuspensionProcessor implements Processor {

    ProducerTemplate producerTemplate = null;
    int counter = 0;

    String routeIdToControl;

    public AbstractRouteSuspensionProcessor(CamelContext camelContext, String routeIdToControl) {
        producerTemplate = camelContext.createProducerTemplate();
        this.routeIdToControl = routeIdToControl;
    }


    @Override
    public void process(Exchange exchange) throws Exception {

        String controlBusPrefix = "controlbus:route?routeId="+routeIdToControl;
        String status = producerTemplate.requestBody(controlBusPrefix+"&action=status", null, String.class);

        boolean serviceUp  = isServiceUp();

        if (serviceUp) {
            if (!status.toLowerCase().equals("started")) {
                producerTemplate.requestBody(controlBusPrefix+"&action=resume", null, String.class);

            }
        }
        else { //Service down
            if (status.toLowerCase().equals("started")) {
                producerTemplate.requestBody(controlBusPrefix+"&action=suspend&async=true", null, String.class);
            }
        }
    }

    abstract protected boolean isServiceUp();
}
