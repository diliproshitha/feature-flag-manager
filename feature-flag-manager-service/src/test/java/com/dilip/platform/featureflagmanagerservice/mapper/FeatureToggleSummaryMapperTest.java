package com.dilip.platform.featureflagmanagerservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureName;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureToggleSummary;

@ExtendWith(MockitoExtension.class)
public class FeatureToggleSummaryMapperTest {

  @InjectMocks
  private FeatureToggleSummaryMapper featureToggleSummaryMapper;

  @Test
  void toSummaryList_givenListOfFeatureToggleEntitiesAndCustomerId_shouldReturnListOfFeatureToggleSummary() {
    final List<FeatureToggle> entities = Instancio.ofList(FeatureToggle.class).size(2).create();
    final UUID customerId = UUID.randomUUID();
    final List<FeatureToggleSummary> result = featureToggleSummaryMapper.toSummaryList(entities, customerId);

    assertThat(result).isNotNull();
    assertThat(result).hasSize(entities.size());
  }

  @Test
  void toFeatureNameStrings_givenListOfFeatureName_shouldReturnListOfFeatureNameStrings() {
    final FeatureToggleSummaryRequestDto requestDto = Instancio.create(FeatureToggleSummaryRequestDto.class);
    final List<FeatureName> features = requestDto.getFeatureRequest().getFeatures();
    final List<String> result = featureToggleSummaryMapper.toFeatureNameStrings(features);

    assertThat(result).isNotNull();
    assertThat(result).hasSize(features.size());
  }
}
