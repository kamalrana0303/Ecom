package com.generic.ecom.dto;

import com.generic.ecom.entity.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {
    private String cartId;
    private List<CartProductDto> cartProducts;
    private int cartFinalValue;

}
