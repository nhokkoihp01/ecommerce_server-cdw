package nlu.edu.vn.ecommerce.service.impl;

import nlu.edu.vn.ecommerce.dto.CartDTO;
import nlu.edu.vn.ecommerce.models.Cart;
import nlu.edu.vn.ecommerce.models.Order;
import nlu.edu.vn.ecommerce.repositories.CartRepository;
import nlu.edu.vn.ecommerce.repositories.OrderRepository;
import nlu.edu.vn.ecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public boolean orderPayment(String userId,CartDTO cartDTO) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            Order order = new Order();
            order.setUserId(userId);
            order.setCartItems(cartDTO.getCartItems());
            order.setTotalPrice(cartDTO.getTotalPrice());
            order.setAddress(cartDTO.getAddress());

            LocalDateTime now = LocalDateTime.now();
            Instant instant = now.toInstant(ZoneOffset.UTC);
            long timestamp = instant.toEpochMilli();
            order.setCreatedAt(timestamp);

            orderRepository.save(order);
            cartRepository.deleteByUserId(userId);
            return true;

        }
        return false;

    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
