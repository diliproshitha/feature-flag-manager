package com.dilip.platform.featureflagmanagerservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.mapper.FeatureToggleSummaryMapper;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;
import com.dilip.platform.featureflagmanagerservice.repository.FeatureToggleRepository;
import com.dilip.platform.featureflagmanagerservice.service.FeatureSummaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class FeatureSummaryServiceImpl implements FeatureSummaryService {

  private final FeatureToggleRepository featureToggleRepository;
  private final FeatureToggleSummaryMapper summaryMapper;

  /**
   * Returns a Feature toggle summary list for a given customer and feature toggle technicalNames
   * @param request
   * @return FeatureToggleSummaryResponseDto
   */
  @Override
  public FeatureToggleSummaryResponseDto getSummaryByCustomer(final FeatureToggleSummaryRequestDto request) {
    final UUID customerId = request.getFeatureRequest().getCustomerId();
    final List<String> featureNames = summaryMapper.toFeatureNameStrings(request.getFeatureRequest()
        .getFeatures());
    final List<FeatureToggle> featureToggles = featureToggleRepository.findByTechnicalNamesIn(featureNames);
    return FeatureToggleSummaryResponseDto.builder()
        .features(summaryMapper.toSummaryList(featureToggles, customerId))
        .build();
  }
}
