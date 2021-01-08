package com.producerconsumer.counter.repository;

import com.producerconsumer.counter.model.ProducerConsumerCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerConsumerCountRepo extends JpaRepository <ProducerConsumerCountDTO, Long> {
}
