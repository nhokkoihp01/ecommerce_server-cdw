package nlu.edu.vn.ecommerce.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@Getter
@Setter
public class Product {
    @Id
    private String id;
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
    private long createdAt;
    private long updateAt;
    private String createBy;
    private String updateBy;
    @NonNull
    private String categoryId;
}
