package com.generic.ecom.service;

import com.generic.ecom.dto.CartDto;

public interface CartService {
    CartDto addToCart(String productId, String cartId);

    CartDto removeFromCart(String productId, String cartId);
}
