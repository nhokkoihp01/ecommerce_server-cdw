package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.dto.UserDTO;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.request.UpdateUserRequest;

import java.util.List;


public interface IUserService {
    boolean updateUser(String id, UpdateUserRequest updatedUser) ;
    boolean updatePassword(String userId,String oldPassword,String newPassword);
    List<UserDTO> getAllUser();


}
