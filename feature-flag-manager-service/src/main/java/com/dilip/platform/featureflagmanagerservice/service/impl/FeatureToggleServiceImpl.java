package com.dilip.platform.featureflagmanagerservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;
import com.dilip.platform.featureflagmanagerservice.exception.ResourceNotFoundException;
import com.dilip.platform.featureflagmanagerservice.mapper.FeatureToggleMapper;
import com.dilip.platform.featureflagmanagerservice.mapper.FeatureToggleSummaryMapper;
import com.dilip.platform.featureflagmanagerservice.mapper.UuidMapper;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;
import com.dilip.platform.featureflagmanagerservice.repository.CustomerRepository;
import com.dilip.platform.featureflagmanagerservice.repository.FeatureToggleRepository;
import com.dilip.platform.featureflagmanagerservice.service.FeatureToggleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class FeatureToggleServiceImpl implements FeatureToggleService {

  private final FeatureToggleRepository featureToggleRepository;
  private final CustomerRepository customerRepository;
  private final FeatureToggleMapper featureToggleMapper;
  private final UuidMapper uuidMapper;
  private final FeatureToggleSummaryMapper summaryMapper;

  /**
   * returns a FeatureToggleDto for given FeatureToggle Id
   * @param id
   * @return FeatureToggleDto
   */
  @Override
  public FeatureToggleDto getById(final UUID id) {
    final FeatureToggle entity = getFeatureToggleById(id);
    return featureToggleMapper.toDto(entity);
  }

  /**
   * save new FeatureToggle
   * Customer are loaded from Customer table and assigned to FeatureToggle
   * @param featureToggleDto
   * @return
   */
  @Override
  @Transactional
  public FeatureToggleDto save(final FeatureToggleDto featureToggleDto) {
    log.info("START: save feature toggle");
    final List<Customer> customers = customerRepository
        .findAllById(uuidMapper.toUuidList(featureToggleDto.getCustomerIds()));
    final FeatureToggle featToggleEntity = featureToggleMapper
        .toEntity(featureToggleDto, new FeatureToggle());
    featToggleEntity.setCustomers(customers);
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully saved feature toggle. id: {}", response.getId());
    log.info("END: save feature toggle");
    return featureToggleMapper.toDto(response);
  }

  /**
   * Updates the given feature toggle
   * loads the existing FeatureToggle and associate customers and updates the FeatureToggle
   * @param featureToggleDto
   * @return FeatureToggleDto
   */
  @Override
  @Transactional
  public FeatureToggleDto update(final FeatureToggleDto featureToggleDto) {
    log.info("START: update feature toggle");
    final List<Customer> customers = customerRepository
        .findAllById(uuidMapper.toUuidList(featureToggleDto.getCustomerIds()));
    final FeatureToggle featToggleEntity = featureToggleMapper.toEntity(featureToggleDto,
        getFeatureToggleById(featureToggleDto.getIdAsUuid()));
    featToggleEntity.setCustomers(customers);
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully updated feature toggle. id: {}", response.getId());
    log.info("END: update feature toggle");
    return featureToggleMapper.toDto(response);
  }

  /**
   * returns a FeatureToggle for given id
   * if not found throws a ResourceNotFoundException
   * @param id
   * @return FeatureToggle
   */
  private FeatureToggle getFeatureToggleById(final UUID id) {
    return featureToggleRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cannot find a FeatureToggle with id: " + id));
  }

  /**
   * Sets FeatureToggle status to ToggleStatus.ARCHIVED
   * @param id
   * @return FeatureToggleDto
   */
  @Override
  @Transactional
  public FeatureToggleDto archive(final UUID id) {
    log.info("START: archive feature toggle");
    FeatureToggle featToggleEntity = getFeatureToggleById(id);
    featToggleEntity.setStatus(ToggleStatus.ARCHIVED);
    final FeatureToggle response = featureToggleRepository.save(featToggleEntity);
    log.info("Successfully archived feature toggle. id: {}", response.getId());
    log.info("END: archive feature toggle");
    return featureToggleMapper.toDto(response);
  }

  /**
   * Returns a Feature toggle summary list for a given customer and feature toggle technicalNames
   * @param requestWrapper
   * @return FeatureToggleSummaryResponseDto
   */
  @Override
  public FeatureToggleSummaryResponseDto getSummaryList(final FeatureToggleSummaryRequestDto requestWrapper) {
    final UUID customerId = requestWrapper.getFeatureRequest().getCustomerId();
    final List<String> featureNames = summaryMapper.toFeatureNameStrings(requestWrapper.getFeatureRequest()
        .getFeatures());
    final List<FeatureToggle> featureToggles = featureToggleRepository.findByTechnicalNamesIn(featureNames);
    return FeatureToggleSummaryResponseDto.builder()
        .features(summaryMapper.toSummaryList(featureToggles, customerId))
        .build();
  }
}
