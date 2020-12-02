//package com.example.orderservice.fakeData;
//
//import com.example.orderservice.models.Order;
//import com.example.orderservice.models.Product;
//import com.example.orderservice.repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CommandLineStartupRunner implements CommandLineRunner {
//
//    @Autowired
//    private OrderRepository repository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        System.out.println("Initializing data.....");
//
//        Product productOne= new Product();
//
//        Order orderOne = new Order();
//        orderOne.setOrderNumber("011220201855");
//        orderOne.setUserId(1L);
//        orderOne.setProductList();
//
//        repository.save(orderOne);
//
//
//    }
//}
