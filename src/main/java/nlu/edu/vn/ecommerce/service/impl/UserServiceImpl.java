package nlu.edu.vn.ecommerce.service.impl;

import lombok.extern.log4j.Log4j2;

import nlu.edu.vn.ecommerce.dto.UserDTO;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.repositories.UserRepository;
import nlu.edu.vn.ecommerce.request.UpdateUserRequest;
import nlu.edu.vn.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public boolean updateUser(String id, UpdateUserRequest updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        log.error(optionalUser);

        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setImage(updatedUser.getImage());
        user.setNumberPhone(updatedUser.getNumberPhone());

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean deleteUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getRoles().contains("ROLE_ADMIN")) {
                return false;
            }
            else{
                userRepository.delete(user);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean updatePassword(String userId, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return false;
        }
        if (!passwordEncoder.matches(oldPassword, user.get().getPassword())) {
            return false;
        }
        user.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user.get());
        return true;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(UserDTO::fromToUserDTO).collect(Collectors.toList());
        return userDTOs;
    }
}
