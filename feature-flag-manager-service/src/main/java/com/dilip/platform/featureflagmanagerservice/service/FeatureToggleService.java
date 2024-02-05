package com.dilip.platform.featureflagmanagerservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;

public interface FeatureToggleService {

  FeatureToggleDto getById(UUID id);

  FeatureToggleDto save(FeatureToggleDto featureToggleDto);

  FeatureToggleDto update(FeatureToggleDto featureToggleDto);

  FeatureToggleDto archive(UUID id);

  FeatureToggleSummaryResponseDto getSummaryList(FeatureToggleSummaryRequestDto requestWrapper);

  Page<FeatureToggleDto> getByPage(Pageable pageable);
}
