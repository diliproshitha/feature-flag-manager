package com.dilip.platform.featureflagmanagerservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;
import com.dilip.platform.featureflagmanagerservice.exception.ResourceNotFoundException;
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
    final FeatureToggle featToggleEntity = featureToggleMapper
        .toEntity(featureToggleDto, new FeatureToggle());
    featToggleEntity.setCustomers(new HashSet<>(customers));
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully saved feature toggle. id: {}", response.getId());
    log.info("END: save feature toggle");
    return featureToggleMapper.toDto(response, new FeatureToggleDto());
  }

  @Transactional
  public FeatureToggleDto update(final FeatureToggleDto featureToggleDto) {
    log.info("START: update feature toggle");
    final List<Customer> customers = customerRepository
        .findAllById(uuidMapper.toUuidList(featureToggleDto.getCustomerIds()));
    final FeatureToggle featToggleEntity = featureToggleMapper.toEntity(featureToggleDto,
        getFeatureToggleById(featureToggleDto.getIdAsUuid()));
    featToggleEntity.setCustomers(new HashSet<>(customers));
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully updated feature toggle. id: {}", response.getId());
    log.info("END: update feature toggle");
    return featureToggleMapper.toDto(response, new FeatureToggleDto());
  }

  private FeatureToggle getFeatureToggleById(final UUID id) {
    return featureToggleRepository.findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("Cannot find a FeatureToggle with id: " + id));
  }

  @Transactional
  public FeatureToggleDto archive(final UUID id) {
    log.info("START: archive feature toggle");
    FeatureToggle featToggleEntity = getFeatureToggleById(id);
    featToggleEntity.setStatus(ToggleStatus.ARCHIVED);
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully archived feature toggle. id: {}", response.getId());
    log.info("END: archive feature toggle");
    return featureToggleMapper.toDto(response, new FeatureToggleDto());
  }
}
