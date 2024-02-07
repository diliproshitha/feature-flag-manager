package com.dilip.platform.featureflagmanagerservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureName;
import com.dilip.platform.featureflagmanagerservice.model.embeddable.FeatureToggleSummary;

@ExtendWith(MockitoExtension.class)
class FeatureToggleSummaryMapperTest {

  @InjectMocks
  private FeatureToggleSummaryMapper featureToggleSummaryMapper;

  @Test
  void toSummaryList_givenValidFeatures_shouldReturnActiveFeatureToggleSummary() {
    final Customer customer = Instancio.create(Customer.class);
    final FeatureToggle entity = Instancio.create(FeatureToggle.class);
    entity.setStatus(ToggleStatus.ACTIVE);
    entity.setCustomers(Collections.singletonList(customer));
    final Date date = new Date((new Date()).getYear() + 2, 1, 1);
    entity.setExpiresOn(date.toInstant());
    final List<FeatureToggleSummary> result = featureToggleSummaryMapper.toSummaryList(
        Collections.singletonList(entity), customer.getIdAsUuid());

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).isActive()).isTrue();
  }

  @Test
  void toSummaryList_givenANonRelatedFeatureToCustomer_shouldReturnInactiveSummary() {
    final UUID customerId = UUID.randomUUID();
    final FeatureToggle entity = Instancio.create(FeatureToggle.class);
    entity.setStatus(ToggleStatus.ACTIVE);
    final Date date = new Date((new Date()).getYear() + 1, 1, 1);
    entity.setExpiresOn(date.toInstant());
    final List<FeatureToggleSummary> result = featureToggleSummaryMapper
        .toSummaryList(Collections.singletonList(entity), customerId);

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).isActive()).isFalse();
  }

  @Test
  void toSummaryList_givenExpiredFeature_shouldReturnInactiveSummary() {
    final Customer customer = Instancio.create(Customer.class);
    final FeatureToggle entity = Instancio.create(FeatureToggle.class);
    entity.setStatus(ToggleStatus.ACTIVE);
    entity.setCustomers(Collections.singletonList(customer));
    final Date date = new Date((new Date()).getYear() - 2, 1, 1);
    entity.setExpiresOn(date.toInstant());
    final List<FeatureToggleSummary> result = featureToggleSummaryMapper
        .toSummaryList(Collections.singletonList(entity), customer.getIdAsUuid());

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).isActive()).isFalse();
  }

  @Test
  void toSummaryList_givenArchivedFeature_shouldReturnInactiveSummary() {
    final Customer customer = Instancio.create(Customer.class);
    final FeatureToggle entity = Instancio.create(FeatureToggle.class);
    entity.setStatus(ToggleStatus.ARCHIVED);
    entity.setCustomers(Collections.singletonList(customer));
    final Date date = new Date(2033, 1, 1);
    entity.setExpiresOn(date.toInstant());
    final List<FeatureToggleSummary> result = featureToggleSummaryMapper
        .toSummaryList(Collections.singletonList(entity), customer.getIdAsUuid());

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0).isActive()).isFalse();
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
