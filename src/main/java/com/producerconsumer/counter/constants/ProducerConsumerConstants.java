package com.producerconsumer.counter.constants;

import java.time.format.DateTimeFormatter;

public class ProducerConsumerConstants {

    public static final String CONSUMER = "Consumer ";

    public static final String PRODUCER = "Producer ";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static int INITIAL_COUNTER = 50;

    public static final int THREAD_LIMIT = 100;
}
