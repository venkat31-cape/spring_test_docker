package com.venkat_test.controller;


import com.venkat_test.entity.OrderEntity;
import com.venkat_test.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrderEntity> saveOrderEntity(@RequestBody OrderEntity orderEntityDto){
        OrderEntity orderEntity = orderService.save(orderEntityDto);
        return ResponseEntity.ok(orderEntity);
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<OrderEntity>> saveOrderEntity(){
        List<OrderEntity> list = orderService.findAll();
        return ResponseEntity.ok(list);
    }

}