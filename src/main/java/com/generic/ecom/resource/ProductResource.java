package com.generic.ecom.resource;

import com.generic.ecom.dto.ProductDto;
import com.generic.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("product")
public class ProductResource {

    @Autowired
    ProductService productService;
    @PostMapping
    public ResponseEntity<?> saveProducts(@RequestBody ProductDto productDto){
        ProductDto product = this.productService.createProduct(productDto);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("product").toUriString());
        return ResponseEntity.created(uri).body(product);
    }

}
