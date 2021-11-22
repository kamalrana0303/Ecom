package com.generic.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String productId;
    @Column(nullable = false)
    private String variantId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Double offeredPrice;
    @Column(nullable = false)
    private Double mrp;
    @Column(nullable = false)
    private Double discount;
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Image> images;


    public void addImage(Image image){
        this.getImages().add(image);
        image.setProduct(this);
    }
    public void removeImage(Image image){
        this.getImages().remove(image);
        image.setProduct(null);
    }
}
