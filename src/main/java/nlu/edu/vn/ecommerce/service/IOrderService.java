package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.dto.CartDTO;

public interface IOrderService {
    boolean orderPayment(String userId, CartDTO cartDTO);
}
