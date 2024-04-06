package com.ecagataydogan.kredinbizdeservice.service;


import com.ecagataydogan.kredinbizdeservice.entity.Address;
import com.ecagataydogan.kredinbizdeservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;

    @Cacheable(value = "#addresses")
    public List<Address> getAll() {
        log.info("db");
        return addressRepository.findAll();
    }
}
