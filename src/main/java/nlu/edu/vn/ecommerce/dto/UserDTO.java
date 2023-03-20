package nlu.edu.vn.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.User;

import java.util.List;


@Builder
@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String image;
    private String firstName;
    private String numberPhone;
    private List<String> roles;


    public static ResponseObject from(User user) {
        return new ResponseObject("ok","thành công",builder()
                .id(user.getId())
                .username(user.getUsername())
                .image(user.getImage())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .numberPhone(user.getNumberPhone())
                .roles(user.getRoles())
                .build());
    }
}
