package com.hiusahald.shop_online.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.hiusahald.shop_online.dto.response.UploadResponse;
import com.hiusahald.shop_online.exceptions.FileUploadingException;
import com.hiusahald.shop_online.util.CommonUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    @Async
    public UploadResponse upload(@NonNull MultipartFile file) {
        try {
            Map<String, Object> properties = new HashMap<>();
            String filename = UUID.randomUUID().toString();
            properties.put("public_id", filename);
            properties.put("overwrite", true);
            Map<?, ?> uploader = this.cloudinary.uploader()
                    .upload(file.getBytes(), properties);
            return UploadResponse.builder()
                    .secureUrl(uploader.get("secure_url").toString())
                    .format(uploader.get("format").toString())
                    .originalFilename(uploader.get("original_filename").toString())
                    .publicId(uploader.get("public_id").toString())
                    .resourceType(uploader.get("resource_type").toString())
                    .build();
        } catch (IOException e) {
            throw new FileUploadingException("Cannot upload file!", e);
        }
    }

    @Override
    public void delete(String url) {
        String publicId = CommonUtil.getFilename(url);
        try {
            this.cloudinary.uploader()
                    .destroy(publicId, new HashMap<>());
        } catch (IOException e) {
            throw new FileUploadingException("Cannot delete file!", e);
        }
    }

}
