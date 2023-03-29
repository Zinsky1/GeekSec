package com.example.geekseq.DAO;

import com.example.geekseq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class UserRepository implements JpaRepository<User, Long> {
    public abstract User findByUsername(String username);
}
