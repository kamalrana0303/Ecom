package com.generic.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String cartId;
    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<CartProduct> cartProducts= new ArrayList<>();
    private double cartFinalValue;

    public void addCartProduct(CartProduct product){
        this.getCartProducts().add(product);
        product.setCart(this);
    }

    public void removeCartProduct(CartProduct cartProduct){
        this.getCartProducts().remove(cartProduct);
        cartProduct.setCart(null);
    }
}
