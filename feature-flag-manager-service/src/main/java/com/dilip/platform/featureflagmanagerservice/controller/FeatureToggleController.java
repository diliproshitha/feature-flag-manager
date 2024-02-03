package com.dilip.platform.featureflagmanagerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilip.platform.featureflagmanagerservice.constant.EndPoints;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.service.FeatureToggleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.FEATURE_TOGGLE)
public class FeatureToggleController {

  private final FeatureToggleService featureToggleService;

  @PostMapping
  public ResponseEntity<FeatureToggleDto> save(@RequestBody final FeatureToggleDto featureToggleDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(featureToggleService.save(featureToggleDto));
  }
}
