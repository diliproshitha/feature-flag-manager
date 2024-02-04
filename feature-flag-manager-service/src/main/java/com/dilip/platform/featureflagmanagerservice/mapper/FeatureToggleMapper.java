package com.dilip.platform.featureflagmanagerservice.mapper;

import org.springframework.stereotype.Component;

import com.dilip.platform.featureflagmanagerservice.entity.Audit;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeatureToggleMapper {

  /**
   * Maps FeatureToggleDto to a given FeatureToggle entity
   * updates the given entity rather than creating a new entity to support update operations
   * @param dto
   * @param entity
   * @return
   */
  public FeatureToggle toEntity(final FeatureToggleDto dto, final FeatureToggle entity) {
    entity.setDisplayName(dto.getDisplayName());
    entity.setTechnicalName(dto.getTechnicalName());
    entity.setExpiresOn(dto.getExpiresOn());
    entity.setDescription(dto.getDescription());
    entity.setInverted(dto.isInverted());
    entity.setStatus(dto.getStatus());
    return entity;
  }

  /**
   * Maps FeatureToggle entity into FeatureToggleDto
   * @param entity
   * @return
   */
  public FeatureToggleDto toDto(final FeatureToggle entity) {
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
}
