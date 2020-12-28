package com.example.orderservice.repository;

import com.example.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.status = 0")
    Collection<Order> findAllNewOrders();

    @Query("SELECT o FROM Order o WHERE o.status = 1")
    Collection<Order> findAllProcessingOrders();

    @Query("SELECT o FROM Order o WHERE o.status = 2")
    Collection<Order> findAllCompletedOrders();

    Order findByOrderNumber(String orderNumber);
}
