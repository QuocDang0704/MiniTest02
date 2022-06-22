package vn.fis.training.ordermanagement.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.fis.training.ordermanagement.domain.*;
import vn.fis.training.ordermanagement.service.CustomerService;
import vn.fis.training.ordermanagement.service.OrderService;
import vn.fis.training.ordermanagement.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {
    private static final org.slf4j.Logger log  = LoggerFactory.getLogger(OrderServiceImplTest.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    private int size;

    @BeforeEach
    void beforeEach(){
        //size = orderService.getAll().size();
    }

    @Test
    void createOrder() {
        Order order = new Order();
        Customer customer = new Customer();
        customer.setId(1L);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.CANCELLED);
        order.setTotalAmount((double) 0);

        orderService.createOrder(order);
        assertEquals(2, orderService.getAll().size());
    }

    @Test
    void addOrderItem() {
        OrderItem item = new OrderItem();
        Order order = orderService.findById(1L);
        item.setOrder(order);
        item.setAmount(1.0);
        item.setQuantity(2);
        item.setProduct(productService.findById(1L));

        orderService.addOrderItem(order.getId(), item);

    }

    @Test
    void removeOrderItem() {
        OrderItem item = new OrderItem();
        item.setId(1L);
        orderService.removeOrderItem(1L, item)
    }

    @Test
    void updateOrderStatus() {
        Order order = orderService.findById(1L);
        order = orderService.updateOrderStatus(order, OrderStatus.CANCELLED);
        assertEquals(order.getStatus(), OrderStatus.CANCELLED);
    }

    @Test
    void findOrdersBetween() {
        List<Order> orderList = orderService.findOrdersBetween(
                LocalDateTime.of(2022, 01,07, 01,01, 01),
                LocalDateTime.of(2022, 07,07, 01,01, 01));

    }

    @Test
    void findWaitingApprovalOrders() {
        List<Order> lstOrders = orderService.findWaitingApprovalOrders();
        lstOrders.forEach(i ->{
            System.out.println(i.getId());
        });
    }

    @Test
    void findOrdersByOrderStatus() {
        List<Order> lstOrders = orderService.findOrdersByOrderStatus(OrderStatus.CANCELLED);
        lstOrders.forEach(i ->{
            System.out.println(i.getId());
        });
    }

    @Test
    void findOrdersByCustomer() {
        List<Order> lstOrders = orderService.findOrdersByCustomer(customerService.findAll().get(0));
        lstOrders.forEach(i ->{
            System.out.println(i.getId());
        });
    }

    @Test
    void getAll() {
        List<Order> lstOrders = orderService.getAll();
        lstOrders.forEach(i ->{
            System.out.println(i.getId());
        });
    }

    @Test
    void findById() {
        Order order = orderService.findById(1L);
        log.info("Amount: "+order.getTotalAmount());
    }
}