package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.request.UpdateUserRequest;


public interface IUserService {
    boolean updateUser(String id, UpdateUserRequest updatedUser) ;
    boolean updatePassword(String userId,String oldPassword,String newPassword);
;
}
