package com.hiusahald.shop_online.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDto(
        @NotBlank(message = "Name is required!")
        String name,
        @NotBlank(message = "Description is required!")
        String description,
        @Negative(message = "Price cannot negative!")
        @NotBlank(message = "Price is required!")
        Double price,
        @Negative(message = "Stock cannot negative!")
        @NotBlank(message = "Stock is required!")
        int available,
        @NotBlank(message = "Category is required!")
        String category,
        @NotBlank(message = "Thumbnail is required!")
        MultipartFile thumbnail,
        List<MultipartFile> images
) {
}
