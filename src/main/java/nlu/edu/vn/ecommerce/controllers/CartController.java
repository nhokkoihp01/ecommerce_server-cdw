package nlu.edu.vn.ecommerce.controllers;



import nlu.edu.vn.ecommerce.exception.ErrorException;
import nlu.edu.vn.ecommerce.exception.MyException;
import nlu.edu.vn.ecommerce.exception.NotFoundException;
import nlu.edu.vn.ecommerce.models.CartItem;
import nlu.edu.vn.ecommerce.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private ICartService iCartService;
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam("userId") String userId, @RequestBody CartItem cartItem) {
        iCartService.addToCart(userId, cartItem);
        return ResponseEntity.ok().body(new MyException(HttpStatus.OK,"Thêm sản phẩm vào giở hàng thành công"));
    }
    @DeleteMapping("/remove/{userId}/items/{productId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable String userId, @PathVariable String productId) {
        boolean removed = iCartService.removeItemFromCart(userId, productId);
        if (removed) {
            return ResponseEntity.ok().body(new MyException(HttpStatus.OK,"Xóa sản phẩm khỏi giở hàng thành công"));

        } else {
            return ResponseEntity.badRequest().body(new NotFoundException("Không tìm thấy item trong giỏ hàng"));
        }
    }
    @PutMapping("/update/items/{productId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable String productId,
                                                           @RequestParam("userId") String userId,
                                                           @RequestParam("quantity") int quantity) {
        CartItem item = iCartService.updateCartItemQuantityByProductIdAndUserId(productId, userId, quantity);
        if (item == null) {
            return ResponseEntity.badRequest().body(new ErrorException(HttpStatus.NOT_FOUND,"update số lượng thất bại"));
        } else {
            return  ResponseEntity.ok().body(new MyException(HttpStatus.FOUND,"update số lượng tthành công"));
        }
    }
}
