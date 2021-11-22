package com.generic.ecom.entity;

import com.generic.ecom.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageType type;
    @ManyToOne
    @JoinColumn(name="product_image")
    private Product product;

}
