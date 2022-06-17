package com.tickets.tickets.repository;

import com.tickets.tickets.model.TrafficOffence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficOffenceRepository extends JpaRepository<TrafficOffence, Long> {
}
