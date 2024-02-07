package com.dilip.platform.featureflagmanagerservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.mapper.CustomerMapper;
import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;
import com.dilip.platform.featureflagmanagerservice.repository.CustomerRepository;
import com.dilip.platform.featureflagmanagerservice.service.impl.CustomerServiceImpl;
import com.dilip.platform.featureflagmanagerservice.util.TestDataUtil;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private CustomerMapper customerMapper;
  @InjectMocks
  private CustomerServiceImpl customerServiceImpl;

  @Test
  void getByPage_givenPageable_shouldReturnPageOfCustomerDto() {
    final Pageable pageable = PageRequest.of(0, 2);
    final List<Customer> customers = Instancio.ofList(Customer.class).size(2).create();
    final List<CustomerDto> customerDtos = TestDataUtil.customersToDtos(customers);
    final Page<Customer> customerPage = PageableExecutionUtils.getPage(customers, pageable,
        pageable::getPageSize);
    final Page<CustomerDto> customerDtoPage = PageableExecutionUtils.getPage(customerDtos, pageable,
        pageable::getPageSize);

    when(customerRepository.findAll(pageable)).thenReturn(customerPage);
    when(customerMapper.toDto(customers.get(0))).thenReturn(customerDtos.get(0));
    when(customerMapper.toDto(customers.get(1))).thenReturn(customerDtos.get(1));

    final Page<CustomerDto> returnedPage = customerServiceImpl.getByPage(pageable);

    assertThat(returnedPage).isEqualTo(customerDtoPage);
  }
}
