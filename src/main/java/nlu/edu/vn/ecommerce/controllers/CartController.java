package nlu.edu.vn.ecommerce.controllers;


import nlu.edu.vn.ecommerce.exception.ErrorException;
import nlu.edu.vn.ecommerce.exception.MyException;
import nlu.edu.vn.ecommerce.exception.NotFoundException;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Cart;
import nlu.edu.vn.ecommerce.models.CartItem;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private ICartService iCartService;

    @GetMapping("/{userId}")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> getCartByUserId(@AuthenticationPrincipal User user,@PathVariable("userId") String userId) {
        List<Cart> carts = iCartService.getCartByUserId(userId);
        if(!carts.isEmpty()){
            return ResponseEntity.ok().body(new ResponseObject("oke", "Thành công", carts));
        }
        else{
            return ResponseEntity.ok().body(new ResponseObject("NOT_FOUND", "Không có sản phẩm trong giỏ hàng", null));
        }


    }

    @PostMapping("/add")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> addToCart(@AuthenticationPrincipal User user, @RequestParam("userId") String userId, @RequestBody CartItem cartItem) {
        iCartService.addToCart(userId, cartItem);
        return ResponseEntity.ok().body(new MyException(HttpStatus.OK, "Thêm sản phẩm vào giở hàng thành công"));
    }

    @DeleteMapping("/remove/{userId}/items/{productId}")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> removeItemFromCart(@AuthenticationPrincipal User user,
                                                @PathVariable("userId") String userId,
                                                @PathVariable String productId) {
        boolean removed = iCartService.removeItemFromCart(userId, productId);
        if (removed) {
            return ResponseEntity.ok().body(new MyException(HttpStatus.OK, "Xóa sản phẩm khỏi giở hàng thành công"));

        } else {
            return ResponseEntity.badRequest().body(new NotFoundException("Không tìm thấy item trong giỏ hàng"));
        }
    }

    @PutMapping("/update/items/{productId}")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> updateCartItemQuantity(@AuthenticationPrincipal User user, @PathVariable String productId,
                                                    @RequestParam("userId") String userId,
                                                    @RequestParam("quantity") int quantity) {
        CartItem item = iCartService.updateCartItemQuantityByProductIdAndUserId(productId, userId, quantity);
        if (item == null) {
            return ResponseEntity.badRequest().body(new ErrorException(HttpStatus.NOT_FOUND, "update số lượng thất bại"));
        } else {
            return ResponseEntity.ok().body(new MyException(HttpStatus.FOUND, "update số lượng tthành công"));
        }
    }

}
