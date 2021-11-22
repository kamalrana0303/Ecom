package com.generic.ecom.resource;

import com.generic.ecom.dto.CartDto;
import com.generic.ecom.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("cart")
public class CartResource {
    @Autowired
    CartService cartService;
    @GetMapping("add")
    public ResponseEntity<?> addProductToCart( @RequestParam("productId") String productId,@RequestParam("cartId") String cartId){
        CartDto cartDto= this.cartService.addToCart(productId, cartId);
        if(cartId.isBlank()){
            return ResponseEntity.created(URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/add").toUriString())).body(cartDto);
        }
        return ResponseEntity.ok().body(cartDto);
    }
    @GetMapping("remove")
    public ResponseEntity<?> removeProductFromCart(@RequestParam("productId") String productId, @RequestParam("cartId") String cartId){
        CartDto cartDto=this.cartService.removeFromCart(productId, cartId);
        return ResponseEntity.ok().body(cartDto);
    }
}
