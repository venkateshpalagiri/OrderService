package com.venkatesh.OrderService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    @Column(name = "PRODUCT_ID")
    private long productId;
    @Column(name = "PRODUCT_QUANTITY")
    private long productQuantity;
    @Column(name = "ORDER_DATE")
    private Instant orderDate;
    @Column(name = "ORDER_STATUS")
    private String orderStatus;
    @Column(name = "ORDER_AMOUNT")
    private long orderAmount;
}
