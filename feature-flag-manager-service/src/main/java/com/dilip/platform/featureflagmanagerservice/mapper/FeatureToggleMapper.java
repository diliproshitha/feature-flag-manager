package com.dilip.platform.featureflagmanagerservice.mapper;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dilip.platform.featureflagmanagerservice.entity.Audit;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureToggleSummary;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeatureToggleMapper {

  public FeatureToggle toEntity(final FeatureToggleDto dto, final FeatureToggle entity) {
    entity.setDisplayName(dto.getDisplayName());
    entity.setTechnicalName(dto.getTechnicalName());
    entity.setExpiresOn(dto.getExpiresOn());
    entity.setDescription(dto.getDescription());
    entity.setInverted(dto.isInverted());
    entity.setStatus(dto.getStatus());
    return entity;
  }

  public FeatureToggleDto toDto(FeatureToggle entity) {
    final FeatureToggleDto dto = new FeatureToggleDto();
    dto.setId(entity.getIdAsUuid());
    dto.setDisplayName(entity.getDisplayName());
    dto.setTechnicalName(entity.getTechnicalName());
    dto.setExpiresOn(entity.getExpiresOn());
    dto.setDescription(entity.getDescription());
    dto.setInverted(entity.isInverted());
    dto.setStatus(entity.getStatus());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setModifiedAt(entity.getModifiedAt());
    dto.setCustomerIds(entity.getCustomers().stream().map(Audit::getId).toList());
    return dto;
  }

  public List<FeatureToggleDto> toDtoList(final List<FeatureToggle> entities) {
    return entities.stream()
        .map(this::toDto)
        .toList();
  }

  public List<FeatureToggleSummary> toSummaryList(final List<FeatureToggle> entities,
      final UUID customerId) {
    return entities.stream()
        .map(entity -> toSummary(entity, customerId))
        .toList();
  }

  private FeatureToggleSummary toSummary(final FeatureToggle entity, final UUID customerId) {
    return FeatureToggleSummary.builder()
        .name(entity.getTechnicalName())
        .active(isFeatureActive(entity, customerId))
        .inverted(entity.isInverted())
        .expired(isFeatureExpired(entity.getExpiresOn()))
        .build();
  }

  private boolean isFeatureExpired(final Instant expiresOn) {
    return Instant.now().compareTo(expiresOn) >= 0;
  }

  // TODO - implement
  private boolean isFeatureActive(final FeatureToggle featureToggle, final UUID customerId) {
    return true;
  }
}
