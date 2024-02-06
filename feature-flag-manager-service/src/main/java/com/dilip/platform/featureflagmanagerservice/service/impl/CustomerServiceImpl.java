package com.dilip.platform.featureflagmanagerservice.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.mapper.CustomerMapper;
import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;
import com.dilip.platform.featureflagmanagerservice.repository.CustomerRepository;
import com.dilip.platform.featureflagmanagerservice.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Override
  public Page<CustomerDto> getByPage(final Pageable pageable) {
    final Page<Customer> customerPage = customerRepository.findAll(pageable);
    return customerPage.map(customerMapper::toDto);
  }
}
