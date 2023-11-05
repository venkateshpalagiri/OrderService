package com.venkatesh.OrderService.service;

import com.venkatesh.OrderService.entity.Order;
import com.venkatesh.OrderService.exception.CustomException;
import com.venkatesh.OrderService.external.client.PaymentService;
import com.venkatesh.OrderService.external.client.ProductService;
import com.venkatesh.OrderService.external.request.PaymentRequest;
import com.venkatesh.OrderService.model.OrderRequest;
import com.venkatesh.OrderService.model.OrderResponse;

import com.venkatesh.OrderService.repository.OrderRepository;

import com.venkatesh.ProductService.model.ProductResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        //Order Entity -> Save the data with Status Order Created
        //Product Service -> Block Products (Reduce the Quantity)
        //Payment Service -> Payments -> Success -> COMPLETE,   Else CANCELLED

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>Placing Order Request: {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());

        log.info(">>>>>>>>>>>>>>>>>>>>Creating Order with Status CREATED");


        Order order= Order.builder().amount(orderRequest.getTotalAmount())
                .orderDate(Instant.now()).orderStatus("CREATED")
                .quantity(orderRequest.getQuantity()).build();
        order=orderRepository.save(order);
        log.info(">>>>>>>>>>>>>>>>Order placed successfully: {}",order.getId());


        PaymentRequest paymentRequest=PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus=null;
        try {
            paymentService.doPayment(paymentRequest );
            log.info("Payment done successfully. Changing the order status");
            orderStatus="PLACED";

        } catch (Exception e){
            log.error(">>>>>>>>>>>>>>>>>>Error occurred in payment. Changing order status");
            orderStatus="PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order palced successfully with Order Id: {}",order.getId());
        return order.getId() ;
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info(">>>>>>>>>>>>>>>>>Get order details for Order Id : {}",orderId);
        Order order=orderRepository.findById(orderId).orElseThrow(()->new CustomException("Order not found with given Id"+orderId,"NOT_FOUND",404));

        log.info(">>>>>>>>>>>>>>>>>>>>>>Invoking Product service to fetch the product for id {} ",order.getId());
        ProductResponse productResponse= restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getId(),ProductResponse.class);


        OrderResponse.ProductDetails productDetails=OrderResponse.ProductDetails.builder()
                .productId(productResponse.getProductId())
                .productName(productResponse.getProductName())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .build();
        log.info(">>>>>>>>>>>>>>>>>>>>>>Getting payment infor form payment Service");




        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .build();
        return orderResponse;
    }
}
