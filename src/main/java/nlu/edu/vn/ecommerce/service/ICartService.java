package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.models.CartItem;

public interface ICartService {
    void addToCart(String userId, CartItem cartItem);
    boolean removeItemFromCart(String userId, String productId);
    CartItem updateCartItemQuantityByProductIdAndUserId(String productId, String userId, int quantity);

}
