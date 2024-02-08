package com.dilip.platform.featureflagmanagerservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilip.platform.featureflagmanagerservice.constant.EndPoints;
import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;
import com.dilip.platform.featureflagmanagerservice.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(EndPoints.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping("/byPage")
  @Operation(summary = "Get Customers by page", description = "Get Customers for given page and size.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Customer page for given page and size."),
      @ApiResponse(responseCode = "500", description = "Internal Server Error"),
  })
  public ResponseEntity<Page<CustomerDto>> byPage(final Pageable pageable) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(customerService.getByPage(pageable));
  }

}
