package com.generic.ecom.service.impl;

import com.generic.ecom.dto.ProductDto;
import com.generic.ecom.entity.Product;
import com.generic.ecom.repository.ProductRepository;
import com.generic.ecom.service.ProductService;
import com.generic.ecom.utility.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl  implements ProductService {
    @Autowired
    ProductRepository productRepository;


    @Override
    public ProductDto createProduct(ProductDto productDto){
        ModelMapper mapper= new ModelMapper();
        Product product = mapper.map(productDto, Product.class);
        product.setProductId(Utils.generateUniqueProductId());
        product.setVariantId("1 year subscription");
        product.getImages().forEach(x->x.setProduct(product));
        Product save = this.productRepository.save(product);
        return mapper.map(save,ProductDto.class);
    }
}
