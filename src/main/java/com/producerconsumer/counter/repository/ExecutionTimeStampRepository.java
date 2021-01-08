package com.producerconsumer.counter.repository;

import com.producerconsumer.counter.model.ExecutionTimeStampDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionTimeStampRepository extends JpaRepository<ExecutionTimeStampDTO, Long> {
}
