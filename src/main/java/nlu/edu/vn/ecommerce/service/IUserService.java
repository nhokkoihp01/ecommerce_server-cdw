package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.dto.UpdateUserDTO;


public interface IUserService {
    boolean updateUser(String id, UpdateUserDTO updatedUser) ;
}
