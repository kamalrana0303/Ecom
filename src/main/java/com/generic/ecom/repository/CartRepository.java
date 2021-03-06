package com.generic.ecom.repository;

import com.generic.ecom.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    public Cart save(Cart cart);
    public Cart findCartBycartId(String cartId);
}
