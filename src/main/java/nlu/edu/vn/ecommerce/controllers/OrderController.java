package nlu.edu.vn.ecommerce.controllers;

import nlu.edu.vn.ecommerce.dto.CartDTO;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
@RestController
public class OrderController {
    @Autowired
    private IOrderService iOrderService;


    @PostMapping("/payment")
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> payment(@AuthenticationPrincipal User user, @RequestParam("userId") String userId, @RequestBody CartDTO cartDTO){
        if(iOrderService.orderPayment(userId, cartDTO)){
            return ResponseEntity.ok().body(new ResponseObject("oke","Thanh toán thành công",null));
        }
        else{
            return ResponseEntity.ok().body(new ResponseObject("error","Không có sản phẩm thanh toán",null));

        }
    }
}
