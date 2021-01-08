package com.producerconsumer.counter.service;

import com.producerconsumer.counter.model.ProducerConsumerCountDTO;
import com.producerconsumer.counter.repository.ProducerConsumerCountRepo;
import com.producerconsumer.counter.repository.ExecutionTimeStampRepository;
import com.producerconsumer.counter.util.MultithreadedProducerConsumerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.producerconsumer.counter.threads.*;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class MultiThreadedProducerConsumerService {

    private final ProducerConsumerCountRepo paramEntityRepository;
    private final ExecutionTimeStampRepository executionTimeStampRepository;
    private static ExecutorService producerExecutorService;
    private static ExecutorService consumerExecutorService;

    /**
     * @param producerThreadCount Producer thread count between 0 to 50
     * @param consumerThreadCount Consumer thread count between 0 to 50
     */
    public void createProducerConsumerThreads(int producerThreadCount, int consumerThreadCount) {
        insertProducerConsumerThreadCount(producerThreadCount, consumerThreadCount);

        producerExecutorService = Executors.newFixedThreadPool(producerThreadCount);
        consumerExecutorService = Executors.newFixedThreadPool(consumerThreadCount);

        for (int threadCount = 0; threadCount < producerThreadCount; threadCount++) {
            producerExecutorService.submit(new ProducerThread(executionTimeStampRepository));
        }

        for (int threadCount = 0; threadCount < consumerThreadCount; threadCount++) {
            consumerExecutorService.submit(new ConsumerThread(executionTimeStampRepository));
        }
    }


    private void insertProducerConsumerThreadCount(int producerThreadCount, int consumerThreadCount) {
        ProducerConsumerCountDTO producerConsumerCountDTO = new ProducerConsumerCountDTO();
        producerConsumerCountDTO.setProducerThreadCount(producerThreadCount);
        producerConsumerCountDTO.setConsumerThreadCount(consumerThreadCount);
        paramEntityRepository.save(producerConsumerCountDTO);
    }

    /**
     * @param counter Counter value for updating counter between 0 to 100
     * @return true/false if counter is updated with passed count value
     */
    public static boolean updateCounter(int counter) {
        MultithreadedProducerConsumerUtil.counter = new AtomicInteger(counter);
        return true;
    }

    public static void initiateShutDown(){
        Optional.ofNullable(producerExecutorService)
                .filter(executorService -> !executorService.isShutdown())
                .map(executorService -> {
                    log.info("producerExecutorService is going to shutDown");
                    return executorService;
                })
                .ifPresent(ExecutorService::shutdownNow);
        Optional.ofNullable(consumerExecutorService)
                .filter(executorService -> !executorService.isShutdown())
                .map(executorService -> {
                    log.info("consumerExecutorService is going to shutDown");
                    return executorService;
                })
                .ifPresent(ExecutorService::shutdownNow);
    }
}
