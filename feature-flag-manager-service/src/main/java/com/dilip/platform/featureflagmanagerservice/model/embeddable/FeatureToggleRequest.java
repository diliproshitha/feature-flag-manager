package com.dilip.platform.featureflagmanagerservice.model.embeddable;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class FeatureToggleRequest {
  private UUID customerId;
  private List<FeatureName> features;
}
