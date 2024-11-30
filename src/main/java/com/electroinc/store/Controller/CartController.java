package com.electroinc.store.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electroinc.store.Dto.AddItemtoCartRequest;
import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.CartDto;
import com.electroinc.store.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("{userId}")
    public ResponseEntity<CartDto> AddItemToCart(@PathVariable String userId,
            @RequestBody AddItemtoCartRequest request) {
        CartDto cartDto = cartService.AddItemtoCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponseMessage> DeleteItemformCart(@PathVariable String userId,
            @PathVariable int itemId) {
        cartService.RemoveItemtoCart(userId, itemId);
        ApiResponseMessage response = ApiResponseMessage.builder().Message("Item is deleted").status(HttpStatus.OK)
                .sucess(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> ClearCart(@PathVariable String userId) {
        cartService.ClearCart(userId);
        ApiResponseMessage response = ApiResponseMessage.builder().Message("cart is clear").status(HttpStatus.OK)
                .sucess(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<CartDto> GetCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
