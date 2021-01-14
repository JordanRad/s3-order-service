package com.example.orderservice;

import com.example.orderservice.models.Order;
import com.example.orderservice.models.Product;
import com.example.orderservice.models.Status;
import com.example.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.util.Arrays;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository repository;


    private Order orderOne;
    private Order orderTwo;

    @BeforeEach
    public void setup(){
        Product p1 = new Product(1L,"ProductOne",19.99,2);
        Product p2 = new Product(12L,"ProductTwo",9.69,1);
        Product p3 = new Product(30L,"ProductThree",5.59,3);
        Product p4 = new Product(31L,"ProductFour",15.50,1);

        orderOne = new Order();
        orderOne.setStatus(Status.NEW);
        orderOne.setOrderNumber("1111");
        orderOne.setProducts(Arrays.asList(p1,p2));
        orderOne.setUserID(1L);

        orderTwo = new Order();
        orderTwo.setStatus(Status.PROCESSING);
        orderTwo.setOrderNumber("2222");
        orderTwo.setProducts(Arrays.asList(p3,p4));
        orderTwo.setUserID(2L);

        entityManager.persist(orderOne);
        entityManager.persist(orderTwo);
    }

    @Test
    public void Should_Get_A_Single_Order_By_Order_Number() {
        Order order = repository.findByOrderNumber(orderOne.getOrderNumber());
        assertThat(order).isNotNull();
    }

    @Test
    public void Should_Not_Get_Nonexisting_Order_By_Order_Number() {
        Order order = repository.findByOrderNumber("3444t534");
        assertThat(order).isNull();
    }

    @Test
    public void Should_Get_All_New_Orders() {
        Collection<Order> orders = repository.findAllNewOrders();
        assertThat(orders.contains(orderOne));
    }

    @Test
    public void Should_Create_New_Order() {
        Product p = new Product(32L,"Product",1.50,3);

        Order newOrder = new Order();
        newOrder.setStatus(Status.NEW);
        newOrder.setOrderNumber("3333");
        newOrder.setProducts(Arrays.asList(p));
        newOrder.setUserID(1L);

        Order saved = repository.save(newOrder);

        assertThat(saved).isNotNull();
        assertThat(saved.getOrderNumber()).isEqualTo(newOrder.getOrderNumber());
    }


}
