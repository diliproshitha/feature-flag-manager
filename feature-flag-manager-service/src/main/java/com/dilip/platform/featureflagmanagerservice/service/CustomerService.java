package com.dilip.platform.featureflagmanagerservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;

public interface CustomerService {

  Page<CustomerDto> getByPage(Pageable pageable);

}
