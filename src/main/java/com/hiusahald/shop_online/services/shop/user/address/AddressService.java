package com.hiusahald.shop_online.services.shop.user.address;

import com.hiusahald.shop_online.dto.AddressDto;
import com.hiusahald.shop_online.dto.request.AddressRequest;
import com.hiusahald.shop_online.dto.response.PageResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAllAddresses(Authentication auth);

    PageResponse<AddressDto> getAllAddresses(int number, int size, Authentication auth);

    List<AddressDto> addAddress(AddressRequest request, Authentication auth);

    List<AddressDto> updateAddress(Long addressId, AddressRequest request, Authentication auth);

    List<AddressDto> removeAddress(Long addressId, Authentication auth);


}
