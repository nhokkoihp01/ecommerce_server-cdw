package nlu.edu.vn.ecommerce.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import nlu.edu.vn.ecommerce.dto.UserDTO;
import nlu.edu.vn.ecommerce.exception.MyException;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.repositories.UserRepository;
import nlu.edu.vn.ecommerce.request.UpdatePasswordRequest;
import nlu.edu.vn.ecommerce.request.UpdateUserRequest;
import nlu.edu.vn.ecommerce.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user) {
        if (user != null) {
            return ResponseEntity.ok().body(UserDTO.from(user));
        } else {
            return ResponseEntity.ok().body(null);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> getAllUser() {
        List<UserDTO> userList = iUserService.getAllUser();
        if (userList != null) {
            return ResponseEntity.ok().body(new ResponseObject("200", "Thành công", userList));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("400", "Không có user nào", null));
        }
    }


    @GetMapping("/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok().body(UserDTO.from(userRepository.findById(id).orElseThrow()));
    }

    @PutMapping("/update/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user,
                                        @PathVariable("userId") String userId,
                                        @RequestBody UpdateUserRequest updatedUser) {
        boolean result = iUserService.updateUser(userId, updatedUser);

        if (result) {
            return ResponseEntity.ok().body(new MyException(HttpStatus.OK, "Thành công"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable String userId) {
        boolean isDeleted = iUserService.deleteUserById(userId);
        if (isDeleted) {
            return ResponseEntity.ok().body(new MyException(HttpStatus.OK,"Thành công"));
        } else {
            return ResponseEntity.ok().body(new MyException(HttpStatus.UNAUTHORIZED,"Xóa user không thành công"));
        }
    }

    @PostMapping("/update/password/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal User user,
                                            @PathVariable("userId") String userId,
                                            @RequestBody UpdatePasswordRequest request) {
        boolean updated = iUserService.updatePassword(userId, request.getOldPassword(), request.getNewPassword());
        if (updated) {
            return ResponseEntity.ok().body(new ResponseObject("200", "Thành công", null));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("400", "Đổi mật khẩu không thành công", null));
        }
    }
}
