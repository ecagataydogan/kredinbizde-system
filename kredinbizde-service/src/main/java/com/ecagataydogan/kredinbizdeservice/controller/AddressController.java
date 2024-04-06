package com.ecagataydogan.kredinbizdeservice.controller;


import com.ecagataydogan.kredinbizdeservice.entity.Address;
import com.ecagataydogan.kredinbizdeservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {
    private final AddressService addressService;


    @GetMapping
    public List<Address> getAll() {
        return addressService.getAll();
    }
}
