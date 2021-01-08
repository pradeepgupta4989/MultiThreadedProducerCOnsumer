package com.producerconsumer.counter.threads;

import com.producerconsumer.counter.repository.ExecutionTimeStampRepository;
import com.producerconsumer.counter.util.MultithreadedProducerConsumerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.producerconsumer.counter.constants.ProducerConsumerConstants.*;
@Slf4j
@RequiredArgsConstructor
public class ConsumerThread implements Runnable {
    private final ExecutionTimeStampRepository executionTimeStampRepository;
    public void run() {
        try {
            while (true) {
                MultithreadedProducerConsumerUtil.counter = MultithreadedProducerConsumerUtil.queue.take();
                MultithreadedProducerConsumerUtil.counter.getAndDecrement();
                if(MultithreadedProducerConsumerUtil.logAndPersistIfThresholdReached(executionTimeStampRepository,CONSUMER)){
                    break;
                }
            }
        } catch (InterruptedException ex) {
            log.info("Terminating Consumer Thread Forcefully");
        }
    }
}
