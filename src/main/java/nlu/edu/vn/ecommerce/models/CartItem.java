package nlu.edu.vn.ecommerce.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private String productId;
    @NonNull
    private String name;
    @NonNull
    private String image;
    @NonNull
    private double newPrice;
    @NonNull
    private double oldPrice;
    @NonNull
    private String description;
    @NonNull
    private int quantity;
    @NonNull
    private int sale;
    @NonNull
    private String categoryId;


}
