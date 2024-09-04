package com.venkat_test.venkat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="order_entity")
public class OrderEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderName;
    private Long orderType;
    private String orderTypeDes;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", orderType=" + orderType +
                ", orderTypeDes='" + orderTypeDes + '\'' +
                '}';
    }
}