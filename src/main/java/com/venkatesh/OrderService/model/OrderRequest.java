package com.venkatesh.OrderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private long orderId;
    private long productQuantity;
    private long orderAmount;
    private PaymentMode paymentMode;
}
