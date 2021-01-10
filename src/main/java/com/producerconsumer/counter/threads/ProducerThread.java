package com.producerconsumer.counter.threads;

import com.producerconsumer.counter.repository.ExecutionTimeStampRepository;
import com.producerconsumer.counter.util.MultithreadedProducerConsumerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.producerconsumer.counter.constants.ProducerConsumerConstants.*;
@Slf4j
@RequiredArgsConstructor
public class ProducerThread implements Runnable {

    private final ExecutionTimeStampRepository executionTimeStampRepository;

    public void run() {
        try {
            while (true) {
               MultithreadedProducerConsumerUtil multithreadedProducerConsumerUtil = new MultithreadedProducerConsumerUtil();
               MultithreadedProducerConsumerUtil.counter.incrementAndGet();
               if (multithreadedProducerConsumerUtil.logAndPersistIfThresholdReached(executionTimeStampRepository, PRODUCER)) {
                   break;
               }
            }
        } catch (InterruptedException ex) {
            log.info("Terminating Producer Thread forcefully");
        }
    }
}
