package nlu.edu.vn.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupDTO {
    @NotBlank
    @Size(message = "Tài khoản phải từ 3 kí tự trở lên và tối đa là 30 lí tự",min = 3, max = 30)
    private String username;
    @NotBlank
    @Size(max = 60)
    @Email(message = "Email đã tồn tại")
    private String email;
    @NotBlank
    @Size(message = "Mật khẩu phải từ 6 kí tự trở lên và tối đa là 20 lí tự",min = 6, max = 20)
    private String password;

}
