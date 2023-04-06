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
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String numberPhone;

    @NonNull
    private List<CartItem> cartItems;
    private double totalPrice;
    @NonNull
    private String address;

}
