package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.dto.CartDTO;
import nlu.edu.vn.ecommerce.models.Cart;
import nlu.edu.vn.ecommerce.models.CartItem;

import java.util.List;

public interface ICartService {
    void addToCart(String userId, CartItem cartItem);
    boolean removeItemFromCart(String userId, String productId);
    CartItem updateCartItemQuantityByProductIdAndUserId(String productId, String userId, int quantity);
    List<Cart> getCartByUserId(String userId);


}
