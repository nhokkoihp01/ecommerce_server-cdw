package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.dto.CartDTO;
import nlu.edu.vn.ecommerce.models.Order;

import java.util.List;

public interface IOrderService {
    boolean orderPayment(String userId, CartDTO cartDTO);
    List<Order> getOrdersByUserId(String userId);
    List<Order> getAllOrders();
}
