package nlu.edu.vn.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import nlu.edu.vn.ecommerce.models.User;

import java.util.List;


@Builder
@Data
public class UserDTO {
    private String id;
    private String username;
    private String image;
    private String firstName;
    private String numberPhone;
    private List<String> roles;


    public static UserDTO from(User user) {
        return builder()
                .id(user.getId())
                .username(user.getUsername())
                .image(user.getImage())
                .firstName(user.getFirstName())
                .numberPhone(user.getNumberPhone())
                .roles(user.getRoles())
                .build();
    }
}
