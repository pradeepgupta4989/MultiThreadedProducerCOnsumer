package com.producerconsumer.counter.router;

import com.producerconsumer.counter.service.MultiThreadedProducerConsumerService;
import com.producerconsumer.counter.validation.MultiThreadedProducerConsumerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import static com.producerconsumer.counter.constants.ValidationConstants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/producerConsumer/v1")
public class MultithreadedProducerConsumerRouter {

    private final MultiThreadedProducerConsumerService producerConsumerService;
    private final MultiThreadedProducerConsumerValidator producerConsumerValidationService;

    @PostMapping(value = "/createThreads/{producerThreadCount}/{consumerThreadCount}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> createProducersConsumers(
            @PathVariable("producerThreadCount") final int producerThreadCount,
            @PathVariable("consumerThreadCount") final int consumerThreadCount) {
        Map<String, String> responseMap = new HashMap<>();
        try {
            if (producerConsumerValidationService.validateProducerConsumerThreadCount(producerThreadCount, consumerThreadCount)) {
                producerConsumerService.createProducerConsumerThreads(producerThreadCount, consumerThreadCount);
                responseMap.put(STATUS,SUCCESS);
                return ResponseEntity.created(URI.create("/")).body(responseMap);
            } else {
                responseMap.put(STATUS, INVALID_PRODUCER_CONSUMER_THREAD_COUNT);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
        } catch (Throwable e) {
            responseMap.put(STATUS, FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
        }
    }

    @PutMapping(value = "updateCounter/{counterValue}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateCounter(@PathVariable("counterValue") final int counterValue) {
        Map<String, String> responseMap = new HashMap<>();
        if (producerConsumerValidationService.validateCounterValue(counterValue)) {
            if (MultiThreadedProducerConsumerService.updateCounter(counterValue)) {
                responseMap.put(STATUS,SUCCESS);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put(STATUS, FAILURE);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
            }
        } else {
            responseMap.put(STATUS, INVALID_COUNTER_VALUE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
        }
    }
}
