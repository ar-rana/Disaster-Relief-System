package com.example.server.repository;

import com.example.server.model.HeadQuarters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadQuarterRepository extends JpaRepository<HeadQuarters, Integer> {
}
