package com.generic.ecom.repository;

import com.generic.ecom.entity.CartProduct;
import com.generic.ecom.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    public CartProduct findCartProductByProduct(Product product);
    public void delete(CartProduct cartProduct);
}
