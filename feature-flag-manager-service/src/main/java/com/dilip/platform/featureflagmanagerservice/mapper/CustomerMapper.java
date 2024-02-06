package com.dilip.platform.featureflagmanagerservice.mapper;

import org.springframework.stereotype.Component;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;

@Component
public class CustomerMapper {

  public CustomerDto toDto(final Customer customer) {
    final CustomerDto customerDto = new CustomerDto();
    customerDto.setId(customer.getIdAsUuid());
    customerDto.setFirstName(customer.getFirstName());
    customerDto.setLastName(customer.getLastName());
    return customerDto;
  }

}
