package com.generic.ecom.dto;

import com.generic.ecom.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String productId;
    private String variantId;
    private String productName;
    private Double offeredPrice;
    private Double mrp;
    private Double discount;
    private List<ImageDto> images;

}
