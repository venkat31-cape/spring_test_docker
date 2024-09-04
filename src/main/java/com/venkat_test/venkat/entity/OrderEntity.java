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
    @Transient
    private String orderTypeDes;
}