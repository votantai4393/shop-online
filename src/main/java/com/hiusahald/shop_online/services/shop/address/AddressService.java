package com.hiusahald.shop_online.services.shop.address;

import com.hiusahald.shop_online.dto.request.AddressRequest;
import com.hiusahald.shop_online.dto.response.AddressResponse;
import com.hiusahald.shop_online.dto.response.PageResponse;
import org.springframework.security.core.Authentication;

public interface AddressService {

    AddressResponse getAddress(Long id, Authentication authentication);

    PageResponse<AddressResponse> getAllAddresses(Authentication authentication);

    AddressResponse addAddress(Long userId, AddressRequest addressDto);

}
