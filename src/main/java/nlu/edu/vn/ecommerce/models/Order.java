package nlu.edu.vn.ecommerce.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    @NonNull
    private String userId;
    @NonNull
    private List<CartItem> cartItems;
    @NonNull
    private double totalPrice;
    @NonNull
    private String address;
}
