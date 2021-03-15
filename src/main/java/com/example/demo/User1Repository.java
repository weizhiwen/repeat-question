package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shixin
 * @date 2021/3/13
 */
@Repository
public interface User1Repository extends JpaRepository<User1, Integer> {
}
