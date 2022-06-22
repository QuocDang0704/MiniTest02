package vn.fis.training.ordermanagement.service.impl;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fis.training.ordermanagement.domain.Customer;
import vn.fis.training.ordermanagement.domain.Order;
import vn.fis.training.ordermanagement.domain.OrderItem;
import vn.fis.training.ordermanagement.domain.OrderStatus;
import vn.fis.training.ordermanagement.repository.OrderItemRepository;
import vn.fis.training.ordermanagement.repository.OrderRepository;
import vn.fis.training.ordermanagement.service.OrderService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order addOrderItem(Long orderId, OrderItem orderItem) {
        OrderItem item = orderItemRepository.save(orderItem);
        if (orderId != orderItem.getOrder().getId()) {
            throw new RuntimeException("id order not duplicate");
        }
        Order order = findById(orderId);
        order.setTotalAmount(
                order.getTotalAmount()
                        +
                orderItem.getAmount()*orderItem.getProduct().getPrice());
        return orderRepository.save(order);
    }

    @Override
    public Order removeOrderItem(Long orderId, OrderItem orderItem) {
        OrderItem item = orderItemRepository.save(orderItem);
        if (orderId != orderItem.getOrder().getId()) {
            throw new RuntimeException("id order not duplicate");
        }
        Order order = findById(orderId);
        order.setTotalAmount(
                order.getTotalAmount()
                        -
                        orderItem.getAmount()*orderItem.getProduct().getPrice());
        orderItemRepository.delete(orderItem);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Order order, OrderStatus orderStatus) {
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return orderRepository
                .findAllByCustomerBetween(fromDateTime, toDateTime);
    }

    @Override
    public List<Order> findWaitingApprovalOrders() {
        return orderRepository.findOrdersByStatus(OrderStatus.WAITING_APPROVAL);

    }

    @Override
    public List<Order> findOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository.findOrdersByStatus(orderStatus);
    }

    @Override
    public List<Order> findOrdersByCustomer(Customer customer) {
        return orderRepository.findOrdersByCustomer(customer);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Can't find order"));
    }
}
