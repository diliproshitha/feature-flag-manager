package com.dilip.platform.featureflagmanagerservice.model;

import java.util.List;

import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureToggleSummary;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureToggleSummaryResponseDto {
  private List<FeatureToggleSummary> features;
}
