package com.dilip.platform.featureflagmanagerservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dilip.platform.featureflagmanagerservice.entity.Audit;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;

@ExtendWith(MockitoExtension.class)
class FeatureToggleMapperTest {

  @InjectMocks
  private FeatureToggleMapper featureToggleMapper;

  @Test
  void toEntity_givenFeatureToggleDtoAndFeatureToggleEntity_shouldReturnFeatureToggleEntity() {
    final FeatureToggleDto dto = Instancio.create(FeatureToggleDto.class);
    final FeatureToggle entity = new FeatureToggle();
    final FeatureToggle result = featureToggleMapper.toEntity(dto, entity);

    assertThat(result).isNotNull();
    assertThat(result.getDisplayName()).isEqualTo(dto.getDisplayName());
    assertThat(result.getTechnicalName()).isEqualTo(dto.getTechnicalName());
    assertThat(result.getExpiresOn()).isEqualTo(dto.getExpiresOn());
    assertThat(result.getDescription()).isEqualTo(dto.getDescription());
    assertThat(result.isInverted()).isEqualTo(dto.isInverted());
    assertThat(result.getStatus()).isEqualTo(dto.getStatus());
  }

  @Test
  void toDto_givenFeatureToggleEntity_shouldReturnFeatureToggleDto() {
    final FeatureToggle entity = Instancio.create(FeatureToggle.class);
    final FeatureToggleDto result = featureToggleMapper.toDto(entity);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(entity.getId());
    assertThat(result.getDisplayName()).isEqualTo(entity.getDisplayName());
    assertThat(result.getTechnicalName()).isEqualTo(entity.getTechnicalName());
    assertThat(result.getExpiresOn()).isEqualTo(entity.getExpiresOn());
    assertThat(result.getDescription()).isEqualTo(entity.getDescription());
    assertThat(result.isInverted()).isEqualTo(entity.isInverted());
    assertThat(result.getStatus()).isEqualTo(entity.getStatus());
    assertThat(result.getCreatedAt()).isEqualTo(entity.getCreatedAt());
    assertThat(result.getModifiedAt()).isEqualTo(entity.getModifiedAt());
    assertThat(result.getCustomerIds()).isEqualTo(entity.getCustomers().stream().map(Audit::getId).toList());
  }

}
