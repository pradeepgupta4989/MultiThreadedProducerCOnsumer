package com.producerconsumer.counter.validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MultiThreadedProducerConsumerValidator {

    public boolean validateProducerConsumerThreadCount(final int producerThreadCount, final int consumerThreadCount) {
        log.info("Inside MultiThreadedProducerConsumerValidator.validateProducerConsumerThreadCount :: producerThreadCount : "
                + producerThreadCount + "consumerThreadCount: " + consumerThreadCount);
        return (producerThreadCount > 0 && producerThreadCount < 50
                && consumerThreadCount > 0 && consumerThreadCount < 50);
    }

    public boolean validateCounterValue(final int counter) {
        log.info("Inside MultiThreadedProducerConsumerValidator.validateCounterValue ::: New Counter Value :"+counter);
        return (counter > 0 && counter < 100);
    }
}
