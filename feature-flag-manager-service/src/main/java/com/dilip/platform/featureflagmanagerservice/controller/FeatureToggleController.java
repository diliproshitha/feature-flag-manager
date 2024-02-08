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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPoints.FEATURE_TOGGLE)
@Tag(name = "Feature Toggle Controller", description = "APIs to manage Feature Toggles. "
    + "This includes CRUD operations and archive feature toggle.")
public class FeatureToggleController {

  private final FeatureToggleService featureToggleService;

  @GetMapping("/byId/{id}")
  @Operation(summary = "Get Feature Toggle by Id", description = "Get Feature Toggle by Id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Feature Toggle found for ID",
          content = { @Content(schema = @Schema(implementation = FeatureToggleDto.class),
              mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", description = "Feature Toggle not found for ID"),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<FeatureToggleDto> byId(@PathVariable final UUID id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.getById(id));
  }

  @GetMapping("/byPage")
  @Operation(summary = "Get Feature Toggles by page", description = "Get Feature Toggles for given page and size.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Feature Toggle page for given page and size."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<Page<FeatureToggleDto>> getByPage(final Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.getByPage(pageable));
  }

  @PostMapping
  @Operation(summary = "Save new Feature Toggle", description = "Save new Feature Toggle.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Successfully saved the Feature Toggle",
          content = { @Content(schema = @Schema(implementation = FeatureToggleDto.class),
              mediaType = "application/json") }),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<FeatureToggleDto> save(@RequestBody @Valid final FeatureToggleDto featureToggleDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(featureToggleService.save(featureToggleDto));
  }

  @PutMapping
  @Operation(summary = "Update Feature Toggle", description = "Update Feature Toggle")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully updated the Feature Toggle",
          content = { @Content(schema = @Schema(implementation = FeatureToggleDto.class),
              mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", description = "Feature Toggle not found in the system."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<FeatureToggleDto> update(@RequestBody @Valid final FeatureToggleDto featureToggleDto) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.update(featureToggleDto));
  }

  @PatchMapping("/{id}/archive")
  @Operation(summary = "Archive Feature Toggle", description = "Archive Feature Toggle")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully archived the Feature Toggle",
          content = { @Content(schema = @Schema(implementation = FeatureToggleDto.class),
              mediaType = "application/json") }),
      @ApiResponse(responseCode = "404", description = "Feature Toggle not found in the system."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<FeatureToggleDto> archive(@PathVariable final UUID id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(featureToggleService.archive(id));
  }

}
