package com.example.orderservice.controllers;

import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderRequest;
import com.example.orderservice.models.Status;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOrders() {

        List<Order> orders = new ArrayList<>();
        repository.findAll().forEach(orders::add);

        return new ResponseEntity(orders, HttpStatus.OK);

    }

    @GetMapping("/getAllNew")
    public ResponseEntity<?> getAllNewOrders() {

        List<Order> orders = new ArrayList<>();
        repository.findAllNewOrders().forEach(orders::add);

        return new ResponseEntity(orders, HttpStatus.OK);

    }

    @GetMapping("/getByOrderNumber/{orderNumber}")
    public ResponseEntity<?> getOrderByOrderNumber(@PathVariable String orderNumber) {

        Order order = repository.findByOrderNumber(orderNumber);

        return order != null ? new ResponseEntity(order, HttpStatus.OK) : new ResponseEntity("No such order", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    public ResponseEntity<?> createNewOrder(@RequestBody OrderRequest request) {

        OrderRequest newOrder = request;

        Order order = new Order();
        order.setOrderNumber(request.getOrderNumber());
        order.setShippingAddress(request.getUser().getAddress());
        order.setUserID(request.getUser().getId());
        order.setProducts(request.getProducts());
        order.setStatus(Status.NEW);

        Order savedOrder = repository.save(order);

        return new ResponseEntity<>(String.format("Your order is sent successfully, %s", savedOrder.getOrderNumber()), HttpStatus.OK);
    }

}
