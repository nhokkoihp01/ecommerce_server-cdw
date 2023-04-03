package nlu.edu.vn.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDTO {
    private String image;
    private String firstName;
    private String lastName;
    private String numberPhone;

}
