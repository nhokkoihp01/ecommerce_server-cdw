package nlu.edu.vn.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.User;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String image;
    private String firstName;
    private String lastName;
    private String numberPhone;
    private List<String> roles;


    public static ResponseObject from(User user) {
        return new ResponseObject("ok", "thành công", builder()
                .id(user.getId())
                .username(user.getUsername())
                .image(user.getImage())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .numberPhone(user.getNumberPhone())
                .roles(user.getRoles())
                .build());
    }

    public static UserDTO fromToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setImage(user.getImage());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setNumberPhone(user.getNumberPhone());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

    public User toUser() {
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.getUsername());
        user.setEmail(this.getEmail());
        user.setImage(this.getImage());
        user.setFirstName(this.getFirstName());
        user.setLastName(this.getLastName());
        user.setNumberPhone(this.getNumberPhone());
        user.setRoles(this.getRoles());
        return user;
    }
}
