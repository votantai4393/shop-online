package com.hiusahald.shop_online.dto.response;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseObject {
    private String message;
    private Integer status;
    private Object data;
}
