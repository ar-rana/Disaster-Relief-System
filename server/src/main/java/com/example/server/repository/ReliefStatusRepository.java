package com.example.server.repository;

import com.example.server.model.enums.ReliefStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliefStatusRepository extends JpaRepository<ReliefStatus ,Integer> {
}
