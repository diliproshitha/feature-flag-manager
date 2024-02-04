package com.dilip.platform.featureflagmanagerservice.model;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeatureToggleDto {
  private UUID id;
  private String displayName;
  @NotBlank(message = "Technical name is mandatory")
  private String technicalName;
  private Instant expiresOn;
  private String description;
  @NotNull(message = "Inverted is mandatory")
  private boolean inverted;
  @NotNull(message = "Status is mandatory")
  private ToggleStatus status;
  private List<String> customerIds;
  private Instant createdAt;
  private Instant modifiedAt;

  public String getId() {
    return Objects.nonNull(id) ? id.toString() : null;
  }

  @JsonIgnore
  public UUID getIdAsUuid() {
    return id;
  }
}
