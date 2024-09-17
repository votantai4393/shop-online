package com.hiusahald.shop_online.dto.response;

import lombok.Builder;

@Builder
public record UploadResponse(
        String publicId,
        String secureUrl,
        String originalFilename,
        String resourceType,
        String format
) {
}
