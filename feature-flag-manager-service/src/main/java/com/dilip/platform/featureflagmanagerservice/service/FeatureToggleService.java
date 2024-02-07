package com.dilip.platform.featureflagmanagerservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;

public interface FeatureToggleService {

  FeatureToggleDto getById(UUID id);

  FeatureToggleDto save(FeatureToggleDto featureToggleDto);

  FeatureToggleDto update(FeatureToggleDto featureToggleDto);

  FeatureToggleDto archive(UUID id);

  Page<FeatureToggleDto> getByPage(Pageable pageable);
}
