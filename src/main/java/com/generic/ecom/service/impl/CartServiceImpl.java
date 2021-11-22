package com.generic.ecom.service.impl;

import com.generic.ecom.dto.CartDto;
import com.generic.ecom.entity.Cart;
import com.generic.ecom.entity.CartProduct;
import com.generic.ecom.entity.Product;
import com.generic.ecom.repository.CartProductRepository;
import com.generic.ecom.repository.CartRepository;
import com.generic.ecom.repository.ProductRepository;
import com.generic.ecom.service.CartService;
import com.generic.ecom.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartProductRepository cartProductRepository;

    @Override
    public CartDto addToCart(String productId, String cartId){
        Product product=this.productRepository.findProductByProductId(productId);
        if(product==null){
            throw new RuntimeException("invalid product");
        }

        Cart cart=null;
        if(cartId.isBlank()){
            CartProduct cartProduct= new CartProduct();
            cartProduct.setProduct(product);
            cartProduct.setCartProductId(Utils.generateUniqueProductId());
            cart= new Cart();
            cart.setCartId(Utils.generateUniqueProductId());
            cart.addCartProduct(cartProduct);
            cartProduct.setQuantity(cartProduct.getQuantity()+1);
        }
        else{
            cart=this.cartRepository.findCartBycartId(cartId);
            boolean isOldCP=false;
            for(CartProduct cp: cart.getCartProducts()){
                if(cp.getProduct().getProductId().equals(productId)){
                    cp.setQuantity(cp.getQuantity()+1);
                    isOldCP=true;
                    break;
                }
            }
            if(!isOldCP){
                CartProduct newCp= new CartProduct();
                newCp.setProduct(product);
                newCp.setCartProductId(Utils.generateUniqueProductId());
                newCp.setQuantity(newCp.getQuantity()+1);
                cart.addCartProduct(newCp);
            }
        }
        cart= updateCart(cart);
        return new ModelMapper().map(this.cartRepository.save(cart), CartDto.class);
    }

    private Cart updateCart(Cart cart){
        double cartFinalValue=0;
        for(CartProduct cp: cart.getCartProducts()){
            cartFinalValue+= cp.getProduct().getOfferedPrice()* cp.getQuantity();
        }
        cart.setCartFinalValue(cartFinalValue);
        return cart;
    }

    @Override
    public CartDto removeFromCart(String productId, String cartId){
        Product product=this.productRepository.findProductByProductId(productId);
        if(product==null){
            throw new RuntimeException("invalid product");
        }
        Cart cart=this.cartRepository.findCartBycartId(cartId);
        if(cart==null){
            throw new RuntimeException("invalid cart");
        }
        CopyOnWriteArrayList<CartProduct> cps=new CopyOnWriteArrayList(cart.getCartProducts());
        for(CartProduct cp: cps){
            if(cp.getProduct().getProductId().equals(productId)){
                if(cp.getQuantity()==1){
                    cart.removeCartProduct(cp);
                }
                else{
                    cp.setQuantity(cp.getQuantity()-1);

                }
            }
        }
        updateCart(cart);
        if(cart.getCartProducts().isEmpty()){
            this.cartRepository.delete(cart);
            cart=null;
        }
        else{
            cart=this.cartRepository.save(cart);
        }
        if(cart!=null){
            return new ModelMapper().map(cart, CartDto.class);
        }
        return null;
    }
}
