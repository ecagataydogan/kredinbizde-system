package com.ecagataydogan.kredinbizdeservice.service;


import com.ecagataydogan.kredinbizdeservice.entity.Address;
import com.ecagataydogan.kredinbizdeservice.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;


    @Test
    public void should_return_all_addresses(){

        List<Address> mockAddresses = prepareMockAddresses();

        when(addressRepository.findAll()).thenReturn(mockAddresses);

        List<Address> actualAddresses = addressService.getAll();

        assertNotNull(actualAddresses);
        assertEquals(mockAddresses.size(), actualAddresses.size());
        assertTrue(actualAddresses.containsAll(mockAddresses));
    }

    private List<Address> prepareMockAddresses() {
        List<Address> addresses = new ArrayList<>();
        Address address1 = new Address();
        address1.setId(1L);
        address1.setAddressDescription("description");
        address1.setProvince("province");
        address1.setAddressTitle("title");
        Address address2 = new Address();
        address2.setId(2L);
        address2.setAddressDescription("description");
        address2.setProvince("province");
        address2.setAddressTitle("title");
        addresses.add(address1);
        addresses.add(address2);
        return addresses;
    }

}
