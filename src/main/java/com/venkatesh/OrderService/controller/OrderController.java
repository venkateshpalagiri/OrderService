package com.venkatesh.OrderService.controller;

import com.venkatesh.OrderService.entity.Order;
import com.venkatesh.OrderService.model.OrderRequest;
import com.venkatesh.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId=orderService.placeOrder(orderRequest);
        log.info("Order Id: {}",orderId);

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable long id){
        Order order=orderService.getOrder(id);
        return ResponseEntity.ok("Order retrived");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(long orderId){
        Order order=orderService.deleteOrder(orderId);
        //business logic here for deleting order

    }

}
