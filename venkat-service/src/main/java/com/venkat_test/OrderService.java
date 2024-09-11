package com.venkat_test;

import com.venkat_test.entity.OrderEntity;
import com.venkat_test.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity save(OrderEntity orderEntityDto){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderName(orderEntityDto.getOrderName());
        orderEntity.setOrderType(orderEntityDto.getOrderType());
        orderEntity.setOrderTypeDes(orderEntityDto.getOrderTypeDes());
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    public List<OrderEntity> findAll(){
        return orderRepository.findAll();
    }
}
