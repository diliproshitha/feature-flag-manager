package com.dilip.platform.featureflagmanagerservice.mapper;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureName;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureToggleSummary;

@Component
public class FeatureToggleSummaryMapper {

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

  public List<String> toFeatureNameStrings(final List<FeatureName> features) {
    return features.stream()
        .map(FeatureName::getName)
        .toList();
  }

}
