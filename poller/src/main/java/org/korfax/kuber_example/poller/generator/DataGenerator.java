package org.korfax.kuber_example.poller.generator;

import lombok.extern.slf4j.Slf4j;
import org.korfax.kuber_example.poller.messaging.DataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class DataGenerator {

    private Random rnd = new Random(1234567890);

    private DataProducer dataProducer;

    @Autowired
    public DataGenerator(DataProducer dataProducer) {
        this.dataProducer = dataProducer;
    }

    @Scheduled(cron = "${application.dataGeneratorCronConfig}")
    public void scheduleFixedDelayTask() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < rnd.nextInt(9) + 1; i++) {
            dataList.add("TestStr" + i + "_" + System.currentTimeMillis());
        }
        log.debug("Generated data:\n" + Arrays.deepToString(dataList.toArray()));

        dataProducer.sendData(dataList);
    }
}