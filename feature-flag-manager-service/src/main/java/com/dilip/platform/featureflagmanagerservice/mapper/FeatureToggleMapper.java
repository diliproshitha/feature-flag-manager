package com.dilip.platform.featureflagmanagerservice.mapper;

import org.springframework.stereotype.Component;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeatureToggleMapper {

  private final AuditMapper auditMapper;

  public FeatureToggle toEntity(final FeatureToggleDto dto) {
    final FeatureToggle entity = new FeatureToggle();
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
    dto.setId(entity.getId());
    dto.setDisplayName(entity.getDisplayName());
    dto.setTechnicalName(entity.getTechnicalName());
    dto.setExpiresOn(entity.getExpiresOn());
    dto.setDescription(entity.getDescription());
    dto.setInverted(entity.isInverted());
    dto.setStatus(entity.getStatus());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setModifiedAt(entity.getModifiedAt());
    dto.setCustomerIds(auditMapper.idsToStringList(entity.getCustomers().stream().toList()));
    return dto;
  }
}
