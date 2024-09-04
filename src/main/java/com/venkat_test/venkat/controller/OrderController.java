package com.venkat_test.venkat.controller;

import com.venkat_test.venkat.entity.OrderEntity;
import com.venkat_test.venkat.repository.OrderEntityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class OrderController {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderEntityRepository orderEntityRepository;

    @PostMapping("/save")
    public ResponseEntity<OrderEntity> saveOrderEntity(@RequestBody OrderEntity orderEntityDto){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderName(orderEntityDto.getOrderName());
        orderEntity.setOrderType(orderEntityDto.getOrderType());
        orderEntity.setOrderTypeDes(orderEntityDto.getOrderTypeDes());
        orderEntityRepository.save(orderEntity);
        return ResponseEntity.ok(orderEntity);
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<OrderEntity>> saveOrderEntity(){
        List<OrderEntity> list = orderEntityRepository.findAll();
        return ResponseEntity.ok(list);
    }

}