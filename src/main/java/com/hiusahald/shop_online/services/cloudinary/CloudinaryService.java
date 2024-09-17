package com.hiusahald.shop_online.services.cloudinary;

import com.hiusahald.shop_online.dto.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    UploadResponse upload(MultipartFile file);

    void delete(String ulr);

}
