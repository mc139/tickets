package com.tickets.tickets.repository;

import com.tickets.tickets.model.OffenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  OffenceTypeRepository extends JpaRepository<OffenceType,Long> {
}
