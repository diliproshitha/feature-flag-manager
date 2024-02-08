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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.FEATURE_TOGGLE_SUMMARY)
public class FeatureSummaryController {

  private final FeatureSummaryService featureSummaryService;

  @PostMapping("/byCustomer")
  @Operation(summary = "Get feature summaries", description = "Get feature summaries by technicalName for given customer.\n"
      + "If customer is included in the FeatureToggle and FeatureToggle is not archived and is not expired, then it is considered as active.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Feature Toggle summaries for given customer"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<FeatureToggleSummaryResponseDto> byCustomer(
      @RequestBody final FeatureToggleSummaryRequestDto request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureSummaryService.getSummaryByCustomer(request));
  }
}
