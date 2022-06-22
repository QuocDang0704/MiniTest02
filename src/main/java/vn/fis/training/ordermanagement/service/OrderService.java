package vn.fis.training.ordermanagement.service;

import vn.fis.training.ordermanagement.domain.Customer;
import vn.fis.training.ordermanagement.domain.Order;
import vn.fis.training.ordermanagement.domain.OrderItem;
import vn.fis.training.ordermanagement.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order addOrderItem(Long orderId, OrderItem orderItem);

    Order removeOrderItem(Long orderId, OrderItem orderItem);

    Order updateOrderStatus(Order order, OrderStatus orderStatus);

    List<Order> findOrdersBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime);

    List<Order> findWaitingApprovalOrders();

    List<Order> findOrdersByOrderStatus(OrderStatus orderStatus);

    List<Order> findOrdersByCustomer(Customer customer);
    List<Order> getAll();
    Order findById(Long id);
}
