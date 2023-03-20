package nlu.edu.vn.ecommerce.service.impl;

import nlu.edu.vn.ecommerce.models.Cart;
import nlu.edu.vn.ecommerce.models.CartItem;
import nlu.edu.vn.ecommerce.repositories.CartRepository;
import nlu.edu.vn.ecommerce.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartRepository cartRepository;


    @Override
    public void addToCart(String userId, CartItem cartItem) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCartItems(new ArrayList<>());
        }
        List<CartItem> cartItems = cart.getCartItems();
        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                found = true;
                break;
            }
        }
        if (!found) {
            cartItems.add(cartItem);
        }
        cart.setCartItems(cartItems);
        cartRepository.save(cart);
    }

    @Override
    public boolean removeItemFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            List<CartItem> items = cart.getCartItems();
            Iterator<CartItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProductId().equals(productId)) {
                    iterator.remove();
                    cart.setCartItems(items);
                    cartRepository.save(cart);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CartItem updateCartItemQuantityByProductIdAndUserId(String productId, String userId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                cartRepository.save(cart);
                return item;
            }
        }
        return null;
    }
}
