package com.example.orderservice;

import com.example.orderservice.models.*;
import com.example.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderMvcTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository repository;

    private String toJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private Order orderOne;
    private Order orderTwo;

    @BeforeEach
    public void setup() throws Exception {
        Product p1 = new Product(1L, "ProductOne", 19.99, 2);
        Product p2 = new Product(12L, "ProductTwo", 9.69, 1);
        Product p3 = new Product(30L, "ProductThree", 5.59, 3);
        Product p4 = new Product(31L, "ProductFour", 15.50, 1);

        orderOne = new Order();
        orderOne.setStatus(Status.NEW);
        orderOne.setOrderNumber("1111");
        orderOne.setProducts(Arrays.asList(p1, p2));
        orderOne.setUserID(1L);

        orderTwo = new Order();
        orderTwo.setStatus(Status.PROCESSING);
        orderTwo.setOrderNumber("2222");
        orderTwo.setProducts(Arrays.asList(p3, p4));
        orderTwo.setUserID(2L);

        repository.saveAll(Arrays.asList(orderOne, orderTwo));
    }

    @AfterEach
    public void delete() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void Should_Return_All_New_Orders_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/orders/getAllNew"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].orderNumber", is("1111")));
    }

    @Test
    public void Should_Return_All_New_And_Processing_Orders_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/orders/getAllNewAndProcessing"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void Should_Return_An_Order_By_OrderNumber_And_Status_200() throws Exception {
        this.mockMvc.perform(get("/api/orders/getByOrderNumber/" + orderOne.getOrderNumber()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderNumber", is(orderOne.getOrderNumber())))
                .andExpect(jsonPath("$.userID", is(1)))
                .andExpect(jsonPath("$.products", hasSize(2)));

    }

    @Test
    public void Should_Create_An_Order_And_Status_200() throws Exception {
        Product pOne = new Product(30L, "NewProductOne", 5.59, 3);
        Product pTwo = new Product(31L, "NewProductTwo", 15.50, 1);

        User user = new User();
        user.setId(2L);
        user.setAddress("Address");
        user.setEmail("test@test.com");

        OrderRequest request = new OrderRequest();
        request.setOrderNumber("3333");
        request.setProducts(Arrays.asList(pOne, pTwo));
        request.setUser(user);

        this.mockMvc.perform(post("/api/orders/")
                .contentType(MediaType.APPLICATION_JSON).content(toJsonString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Your order is sent successfully, 3333"));
    }

    @Test
    public void Should_Update_An_Order_And_Status_200() throws Exception {

        this.mockMvc.perform(put("/api/orders/" + orderOne.getOrderNumber() + "?status=PROCESSING")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Order with number 1111 was set to PROCESSING"));
        this.mockMvc.perform(put("/api/orders/" + orderOne.getOrderNumber() + "?status=COMPLETED")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Order with number 1111 was set to COMPLETED"));
        this.mockMvc.perform(put("/api/orders/" + orderOne.getOrderNumber() + "?status=CANCELED")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Order with number 1111 was set to CANCELED"));
    }

}
