package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

import java.util.Optional;

public interface ICustomerService {
    Optional<CustomerDto> getCustomerByMobileNumber(String mobileNumber);
}
