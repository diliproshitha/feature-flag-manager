package com.dilip.platform.featureflagmanagerservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

  @InjectMocks
  private CustomerMapper customerMapper;

  @Test
  void toDto_givenCustomerEntity_shouldReturnCustomerDto() {
    // given
    final Customer customer = Instancio.create(Customer.class);

    // when
    final CustomerDto result = customerMapper.toDto(customer);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(customer.getId());
    assertThat(result.getFirstName()).isEqualTo(customer.getFirstName());
    assertThat(result.getLastName()).isEqualTo(customer.getLastName());
  }
}
