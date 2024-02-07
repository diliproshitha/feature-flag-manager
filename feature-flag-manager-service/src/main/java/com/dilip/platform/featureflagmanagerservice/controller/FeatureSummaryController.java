package com.dilip.platform.featureflagmanagerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilip.platform.featureflagmanagerservice.constant.EndPoints;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;
import com.dilip.platform.featureflagmanagerservice.service.FeatureSummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.FEATURE_TOGGLE_SUMMARY)
public class FeatureSummaryController {

  private final FeatureSummaryService featureSummaryService;

  @PostMapping("/byCustomer")
  public ResponseEntity<FeatureToggleSummaryResponseDto> byCustomer(
      @RequestBody final FeatureToggleSummaryRequestDto request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureSummaryService.getSummaryByCustomer(request));
  }
}
