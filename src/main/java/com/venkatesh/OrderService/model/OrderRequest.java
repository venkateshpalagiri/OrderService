package com.venkatesh.OrderService.model;

import javax.persistence.Column;
import java.time.Instant;

public class OrderRequest {
    private long orderId;
    private long productQuantity;
    private long orderAmount;

}
