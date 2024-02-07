package com.dilip.platform.featureflagmanagerservice.service;

import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;

public interface FeatureSummaryService {
  FeatureToggleSummaryResponseDto getSummaryByCustomer(FeatureToggleSummaryRequestDto request);

}
