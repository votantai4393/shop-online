package com.hiusahald.shop_online.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElement,
        Integer totalPage,
        boolean isFirst,
        boolean isLast
) {
}
