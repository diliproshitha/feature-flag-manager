package com.dilip.platform.featureflagmanagerservice.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeatureToggleDto {
  private UUID id;
  private String displayName;
  @NotNull
  private String technicalName;
  private Instant expiresOn;
  private String description;
  @NotNull
  private boolean inverted;
  private ToggleStatus status;
  private List<String> customerIds;
  private Instant createdAt;
  private Instant modifiedAt;
}
