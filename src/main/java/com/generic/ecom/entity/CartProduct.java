package com.generic.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true,nullable = false)
    private String cartProductId;
    private int quantity;
    @OneToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name="cart")
    private Cart cart;
}
