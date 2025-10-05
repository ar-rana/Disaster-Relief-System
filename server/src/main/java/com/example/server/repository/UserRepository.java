package com.example.server.repository;

import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM users u WHERE u.role = 'PROVIDER' ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    User findRandomProvider();

    @Query(value = "SELECT u FROM users u WHERE u.head_quarters_hq_id = ?1 AND u.role = 'PROVIDER' ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    User findRandomProviderByHeadQuarters(int hqId);
}
