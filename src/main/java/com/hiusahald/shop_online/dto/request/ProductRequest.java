package com.hiusahald.shop_online.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductRequest(
        @NotBlank(message = "Name is required!")
        String name,
        @NotBlank(message = "Price is required!")
        Double price,
        String description,
        @NotBlank(message = "Inventory is required!")
        Integer inventory,
        @NotBlank(message = "Category is required!")
        String categoryName,
        @NotNull(message = "Thumbnail is required!")
        MultipartFile thumbnailFile,
        List<MultipartFile> imageFiles
) {
}
