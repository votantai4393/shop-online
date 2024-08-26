package com.hiusahald.shop_online.services.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface CloudinaryService {

    CompletableFuture<UploadResponse> upload(MultipartFile file);

    void delete(String ulr);

}
