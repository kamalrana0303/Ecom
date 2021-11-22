package com.generic.ecom.dto;

import com.generic.ecom.entity.Cart;
import com.generic.ecom.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartProductDto {
    private String cartProductId;
    private int quantity;
    private ProductDto product;
}
