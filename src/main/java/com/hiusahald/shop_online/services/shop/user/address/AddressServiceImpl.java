package com.hiusahald.shop_online.services.shop.user.address;

import com.hiusahald.shop_online.dto.AddressDto;
import com.hiusahald.shop_online.dto.request.AddressRequest;
import com.hiusahald.shop_online.dto.response.PageResponse;
import com.hiusahald.shop_online.models.user.Address;
import com.hiusahald.shop_online.models.user.AddressRepository;
import com.hiusahald.shop_online.models.user.User;
import com.hiusahald.shop_online.util.MapperUntil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    public List<AddressDto> getAllAddresses(Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<Address> addresses = addressRepository.findAllByUserId(user.getId());
        return addresses.stream()
                .map(MapperUntil::toAddressDto)
                .toList();
    }

    @Override
    public PageResponse<AddressDto> getAllAddresses(int number, int size, Authentication auth) {
        return null;
    }

    @Override
    public List<AddressDto> addAddress(AddressRequest request, Authentication auth) {
        return List.of();
    }

    @Override
    public List<AddressDto> updateAddress(Long addressId, AddressRequest request, Authentication auth) {
        return List.of();
    }

    @Override
    public List<AddressDto> removeAddress(Long addressId, Authentication auth) {
        return List.of();
    }

}
