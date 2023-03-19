package nlu.edu.vn.ecommerce;

import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class Database implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        User user = new User();
//        user.setUsername("user");
//        user.setPassword("{bcrypt}$2a$10$q5m5AELB9KkVRykZI07pWuyGRce/akjthAQPMwJy8tMmgEAKkb25S");
//        user.setRoles(Collections.singletonList("USER_ROLE"));
//        userRepository.save(user);
    }
}