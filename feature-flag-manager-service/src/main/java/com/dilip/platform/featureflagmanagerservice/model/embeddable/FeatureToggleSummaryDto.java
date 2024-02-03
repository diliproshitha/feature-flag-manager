package com.dilip.platform.featureflagmanagerservice.model.embeddable;

import lombok.Data;

@Data
public class FeatureToggleSummaryDto {
  private String name;
  private boolean active;
  private boolean inverted;
  private boolean expired;
}
