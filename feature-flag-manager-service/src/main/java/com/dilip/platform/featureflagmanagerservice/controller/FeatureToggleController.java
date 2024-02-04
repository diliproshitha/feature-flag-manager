package com.dilip.platform.featureflagmanagerservice.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilip.platform.featureflagmanagerservice.constant.EndPoints;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryRequestDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleSummaryResponseDto;
import com.dilip.platform.featureflagmanagerservice.service.impl.FeatureToggleServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.FEATURE_TOGGLE)
public class FeatureToggleController {

  private final FeatureToggleServiceImpl featureToggleService;

  @GetMapping("/byId/{id}")
  public ResponseEntity<FeatureToggleDto> byId(@PathVariable final UUID id) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(featureToggleService.getById(id));
  }

  @PostMapping
  public ResponseEntity<FeatureToggleDto> save(@RequestBody @Valid final FeatureToggleDto featureToggleDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(featureToggleService.save(featureToggleDto));
  }

  @PutMapping
  public ResponseEntity<FeatureToggleDto> update(@RequestBody @Valid final FeatureToggleDto featureToggleDto) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.update(featureToggleDto));
  }

  @PatchMapping("/{id}/archive")
  public ResponseEntity<FeatureToggleDto> archive(@PathVariable final UUID id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.archive(id));
  }

  @PostMapping("/summary")
  public ResponseEntity<FeatureToggleSummaryResponseDto> getSummary(
      @RequestBody final FeatureToggleSummaryRequestDto wrapperDto) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.getSummaryList(wrapperDto));
  }
}
