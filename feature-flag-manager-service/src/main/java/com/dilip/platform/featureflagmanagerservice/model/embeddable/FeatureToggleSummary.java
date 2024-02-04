package com.dilip.platform.featureflagmanagerservice.model.embeddable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureToggleSummary {
  private String name;
  private boolean active;
  private boolean inverted;
  private boolean expired;
}
