package com.dilip.platform.featureflagmanagerservice.model;

import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureToggleRequest;

import lombok.Getter;

@Getter
public class FeatureToggleSummaryRequestDto {

  private FeatureToggleRequest featureRequest;
}
