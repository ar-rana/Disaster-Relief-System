package com.example.server.repository;

import java.util.List;

import com.example.server.model.ReliefReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReliefRequestRepository extends JpaRepository<ReliefReq, Long> {
    List<ReliefReq> findByPoc(String poc);
}
