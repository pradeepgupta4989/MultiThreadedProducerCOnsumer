package com.producerconsumer.counter.util;

import com.producerconsumer.counter.model.ExecutionTimeStampDTO;
import com.producerconsumer.counter.repository.ExecutionTimeStampRepository;
import com.producerconsumer.counter.service.MultiThreadedProducerConsumerService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.producerconsumer.counter.constants.ProducerConsumerConstants.*;

@Slf4j
public class MultithreadedProducerConsumerUtil {

    public static AtomicInteger counter = new AtomicInteger(INITIAL_COUNTER);
    public static final BlockingQueue<AtomicInteger> queue = new ArrayBlockingQueue<>(THREAD_LIMIT);

    public static synchronized boolean logAndPersistIfThresholdReached(
            final ExecutionTimeStampRepository executionTimeStampRepository, final String source) throws InterruptedException {

        log.info(source + " : {}  Counter : {} Time : {}",
                Thread.currentThread().getName(), counter.get(),
                LocalDateTime.now().format(DATE_TIME_FORMATTER));

        Thread.sleep(10);
        if (counter.get() == 0 || counter.get() == 100) {
            ExecutionTimeStampDTO executionTimeStampDTO = new ExecutionTimeStampDTO();
            executionTimeStampDTO.setTimeStamp(Timestamp.valueOf(LocalDateTime.now()));
            executionTimeStampDTO.setThreadDetails(source + Thread.currentThread().getName() + " " + counter.get());
            executionTimeStampRepository.save(executionTimeStampDTO);
            MultiThreadedProducerConsumerService.initiateShutDown();
            return true;
        }
        return false;
    }
}
