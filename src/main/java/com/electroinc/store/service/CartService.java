package com.electroinc.store.service;

import com.electroinc.store.Dto.AddItemtoCartRequest;
import com.electroinc.store.Dto.CartDto;

public interface CartService {
    CartDto AddItemtoCart(String userId, AddItemtoCartRequest addItemtoCartRequest);

    void RemoveItemtoCart(String userId, int cartItemId);

    void ClearCart(String userId);

    CartDto getCartByUser(String userId);
}
