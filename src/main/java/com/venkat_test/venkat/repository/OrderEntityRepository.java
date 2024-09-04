package com.venkat_test.venkat.repository;

import com.venkat_test.venkat.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntityRepository  extends JpaRepository<OrderEntity,Long> {
}