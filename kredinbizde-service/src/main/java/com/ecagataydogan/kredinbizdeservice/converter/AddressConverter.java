package com.ecagataydogan.kredinbizdeservice.converter;

import com.ecagataydogan.kredinbizdeservice.dto.request.AddressRequest;
import com.ecagataydogan.kredinbizdeservice.entity.Address;

public class AddressConverter {
    public Address toAddress(AddressRequest addressRequest) {
        Address address = new Address();
        address.setAddressTitle(addressRequest.getAddressTitle());
        address.setProvince(addressRequest.getProvince());
        address.setAddressDescription(addressRequest.getAddressDescription());
        return address;
    }
}
