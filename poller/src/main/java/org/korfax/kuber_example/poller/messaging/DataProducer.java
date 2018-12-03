package org.korfax.kuber_example.poller.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class DataProducer {

    @Autowired
    private PubsubOutboundGateway messagingGateway;

    public void sendData(List<String> dataList) {
        log.debug("Data to be send:\n" + Arrays.deepToString(dataList.toArray()));
        dataList.forEach(text -> messagingGateway.sendToPubsub(text));
    }

}