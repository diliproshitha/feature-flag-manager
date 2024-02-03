package com.dilip.platform.featureflagmanagerservice.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.mapper.FeatureToggleMapper;
import com.dilip.platform.featureflagmanagerservice.mapper.UuidMapper;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.repository.CustomerRepository;
import com.dilip.platform.featureflagmanagerservice.repository.FeatureToggleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class FeatureToggleService {

  private final FeatureToggleRepository featureToggleRepository;
  private final CustomerRepository customerRepository;
  private final FeatureToggleMapper featureToggleMapper;
  private final UuidMapper uuidMapper;

  @Transactional
  public FeatureToggleDto save(final FeatureToggleDto featureToggleDto) {
    log.info("START: save feature toggle");
    final List<Customer> customers = customerRepository
        .findAllById(uuidMapper.toUuidList(featureToggleDto.getCustomerIds()));
    final FeatureToggle featToggleEntity = featureToggleMapper.toEntity(featureToggleDto);
    featToggleEntity.setCustomers(new HashSet<>(customers));
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully saved feature toggle. id: {}", response.getId());
    log.info("END: save feature toggle");
    return featureToggleMapper.toDto(response);
  }

}
