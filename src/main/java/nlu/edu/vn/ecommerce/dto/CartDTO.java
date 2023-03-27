package nlu.edu.vn.ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import nlu.edu.vn.ecommerce.models.CartItem;

import java.util.List;

@Data
@Getter
@Setter
public class CartDTO {
    @NonNull
    private String userId;
    @NonNull
    private List<CartItem> cartItems;
    private double totalPrice;
    private int totalQuantity;
    @NonNull
    private String address;

}
