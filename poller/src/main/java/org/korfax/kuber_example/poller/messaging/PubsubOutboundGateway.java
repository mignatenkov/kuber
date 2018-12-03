package org.korfax.kuber_example.poller.messaging;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
interface PubsubOutboundGateway {

    void sendToPubsub(String text);
}