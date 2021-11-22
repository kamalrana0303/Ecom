package com.generic.ecom.dto;

import com.generic.ecom.enums.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDto {
    private long id;
    private String url;

    private String type;


}
