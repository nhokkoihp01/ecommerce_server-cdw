package nlu.edu.vn.ecommerce.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import nlu.edu.vn.ecommerce.dto.CartDTO;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Order;
import nlu.edu.vn.ecommerce.models.User;
import nlu.edu.vn.ecommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/order")
@RestController
public class OrderController {
    @Autowired
    private IOrderService iOrderService;


    @PostMapping("/payment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> payment(@AuthenticationPrincipal User user, @RequestParam("userId") String userId, @RequestBody CartDTO cartDTO) {
        if (iOrderService.orderPayment(userId, cartDTO)) {
            return ResponseEntity.ok().body(new ResponseObject("oke", "Thanh toán thành công", null));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("error", "Không có sản phẩm thanh toán", null));

        }
    }

    @GetMapping("/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("#user.id == #userId")
    public ResponseEntity<?> getOrdersByUserId(@AuthenticationPrincipal User user, @PathVariable("userId") String userId) {
        List<Order> orders = iOrderService.getOrdersByUserId(userId);
        if (orders == null) {
            return ResponseEntity.ok().body(new ResponseObject("400", "failed", null));
        }
        return ResponseEntity.ok().body(new ResponseObject("200", "Thành công", orders));

    }

    @GetMapping("")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(new ResponseObject("200", "Thành công", iOrderService.getAllOrders()));
    }
}
