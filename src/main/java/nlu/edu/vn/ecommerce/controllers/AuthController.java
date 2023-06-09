package nlu.edu.vn.ecommerce.controllers;

import lombok.extern.log4j.Log4j2;
import nlu.edu.vn.ecommerce.exception.ErrorException;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.RefreshToken;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.dto.TokenDTO;
import nlu.edu.vn.ecommerce.jwt.JwtHelper;
import nlu.edu.vn.ecommerce.repositories.RefreshTokenRepository;
import nlu.edu.vn.ecommerce.repositories.UserRepository;
import nlu.edu.vn.ecommerce.request.LoginRequest;
import nlu.edu.vn.ecommerce.request.SignupRequest;
import nlu.edu.vn.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@Log4j2
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setOwner(user);
            refreshTokenRepository.save(refreshToken);

            String accessToken = jwtHelper.generateAccessToken(user);
            String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

            return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, refreshTokenString));
        }
        catch (Exception e){
            return ResponseEntity.ok().body(new ErrorException(HttpStatus.UNAUTHORIZED,"unauthorized"));
        }
    }

    @PostMapping("signup")
    @Transactional
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.ok().body(new ResponseObject("ALL_ERROR","Error", bindingResult.getAllErrors().get(0).getDefaultMessage()));
        }

        if(userRepository.existsByUsername(request.getUsername())){
            return ResponseEntity.ok().body(new ResponseObject("USERNAME_FOUNDED","Tài khoản đã tồn tại",null));
        }
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.ok().body(new  ResponseObject("EMAIL_FOUNDED","Email đã tồn tại",null));
        }


        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()),request.getFirstName(), request.getLastName(), request.getNumberPhone());
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        userRepository.save(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setOwner(user);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(user);
        String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

        return ResponseEntity.ok(new TokenDTO(user.getId(), accessToken, refreshTokenString));
    }


}
