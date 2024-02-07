package com.dilip.platform.featureflagmanagerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.mapper.FeatureToggleSummaryMapper;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;
import com.dilip.platform.featureflagmanagerservice.repository.FeatureToggleRepository;
import com.dilip.platform.featureflagmanagerservice.service.impl.FeatureSummaryServiceImpl;

@ExtendWith(MockitoExtension.class)
class FeatureSummaryServiceImplTest {

  @Mock
  private FeatureToggleRepository featureToggleRepository;
  @Mock
  private FeatureToggleSummaryMapper summaryMapper;
  @InjectMocks
  private FeatureSummaryServiceImpl featureSummaryService;


  @Test
  void getSummaryList_givenValidRequest_shouldReturnSummaryResponse() {
    final List<FeatureToggle> featToggles = Instancio.ofList(FeatureToggle.class).size(2).create();

    final FeatureToggleSummaryRequestDto request = Instancio.create(FeatureToggleSummaryRequestDto.class);
    final FeatureToggleSummaryResponseDto response = Instancio.create(FeatureToggleSummaryResponseDto.class);
    when(featureToggleRepository.findByTechnicalNamesIn(any(List.class))).thenReturn(featToggles);
    when(summaryMapper.toSummaryList(any(List.class), any(UUID.class))).thenReturn(response.getFeatures());

    final FeatureToggleSummaryResponseDto returnedResponse = featureSummaryService.getSummaryByCustomer(request);

    assertThat(returnedResponse).isEqualTo(response);
  }
}
