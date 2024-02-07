package com.dilip.platform.featureflagmanagerservice.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.dilip.platform.featureflagmanagerservice.service.FeatureToggleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.FEATURE_TOGGLE)
public class FeatureToggleController {

  private final FeatureToggleService featureToggleService;

  @GetMapping("/byId/{id}")
  public ResponseEntity<FeatureToggleDto> byId(@PathVariable final UUID id) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(featureToggleService.getById(id));
  }

  @GetMapping("/byPage")
  public ResponseEntity<Page<FeatureToggleDto>> getByPage(final Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.getByPage(pageable));
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

}
