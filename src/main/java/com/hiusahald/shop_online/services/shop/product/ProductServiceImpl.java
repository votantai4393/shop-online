package com.hiusahald.shop_online.services.shop.product;

import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.dto.ProductDto;
import com.hiusahald.shop_online.dto.request.ProductRequest;
import com.hiusahald.shop_online.exceptions.ResourceNotFoundException;
import com.hiusahald.shop_online.models.product.*;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.services.cloudinary.CloudinaryService;
import com.hiusahald.shop_online.dto.response.UploadResponse;
import com.hiusahald.shop_online.util.CommonUtil;
import com.hiusahald.shop_online.util.MapperUntil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;

    private Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + name));
    }

    private Image uploadImage(MultipartFile file) {
        UploadResponse response = cloudinaryService.upload(file);
        return Image.builder()
                .url(response.secureUrl())
                .build();
    }

    private Product toProduct(ProductRequest request) {
        return update(request, new Product());
    }

    private Product update(ProductRequest request, Product currentProduct) {
        if (request.description() != null) {
            currentProduct.setDescription(request.description());
        }
        if (request.inventory() != null) {
            currentProduct.setInventory(request.inventory());
        }
        if (request.name() != null) {
            currentProduct.setName(request.name());
        }
        if (request.price() != null) {
            currentProduct.setPrice(request.price());
        }
        if (request.categoryName() != null) {
            Category category = getCategoryByName(request.categoryName());
            currentProduct.setCategory(category);
        }
        if (request.thumbnailFile() != null) {
            Image image = uploadImage(request.thumbnailFile());
            currentProduct.setThumbnail(image);
        }
        if (request.imageFiles() != null) {
            Set<Image> images = request.imageFiles()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(this::uploadImage)
                    .collect(Collectors.toSet());
//            check if currentProduct is persisted or not
            if (currentProduct.getId() == null) {
                currentProduct.setImages(images);
            } else {
                images = images.stream()
                        .peek(image -> image.setProduct(currentProduct))
                        .collect(Collectors.toSet());
                imageRepository.saveAll(images);
            }
        }
        return currentProduct;
    }

    @Override
    @Transactional
    public ProductDto addProduct(ProductRequest request, Authentication auth) {
        User currentUser = (User) auth.getPrincipal();
        CommonUtil.validateAdminAccess(currentUser);
        Product product = toProduct(request);
        product = productRepository.save(product);
        return MapperUntil.mapToProductDto(product);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long productId, ProductRequest request, Authentication auth) {
        User currentUser = (User) auth.getPrincipal();
        CommonUtil.validateAdminAccess(currentUser);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        update(request, product);
        product = productRepository.save(product);
        return MapperUntil.mapToProductDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId, Authentication auth) {
        User currentUser = (User) auth.getPrincipal();
        CommonUtil.validateAdminAccess(currentUser);
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProduct(Long productId, Authentication auth) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        return MapperUntil.mapToProductDto(product);
    }

    @Override
    public PageResponse<ProductDto> getAllProducts(int number, int size) {
        Pageable pageable = PageRequest.of(number, size,
                Sort.Direction.DESC, "createdAt");
        Page<Product> page = productRepository.findAll(pageable);
        return MapperUntil.toPageResponse(page, MapperUntil::mapToProductDto);
    }

    @Override
    public PageResponse<ProductDto> searchProduct(int number, int size,
            String content, String directionSort, String sortBy, Long categoryId) {

        return null;
    }

}
