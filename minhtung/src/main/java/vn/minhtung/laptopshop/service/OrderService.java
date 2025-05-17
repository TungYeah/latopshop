package vn.minhtung.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;

import vn.minhtung.laptopshop.domain.Order;
import vn.minhtung.laptopshop.domain.OrderDetail;
import vn.minhtung.laptopshop.repository.OrderDetailRepository;
import vn.minhtung.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

    private final DaoAuthenticationProvider authProvider;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository,
            DaoAuthenticationProvider authProvider) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.authProvider = authProvider;
    }

    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.getOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public void deleteOrder(long id) {
        Optional<Order> orderOptional = this.getOrderById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }
        this.orderRepository.deleteById(id);
    }
}
