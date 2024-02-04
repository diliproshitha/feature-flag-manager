package com.dilip.platform.featureflagmanagerservice.config;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dilip.platform.featureflagmanagerservice.exception.ResourceNotFoundException;
import com.dilip.platform.featureflagmanagerservice.model.ErrorResponseDto;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponseDto handleResourceNotFound(final ResourceNotFoundException ex) {
    return ErrorResponseDto.builder()
        .message(ex.getMessage())
        .code(HttpStatus.NOT_FOUND.name())
        .build();
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
      final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
    final List<String> validationErrors = getValidationErrors(ex);
    final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .message(validationErrors.toString())
        .code(HttpStatus.BAD_REQUEST.name())
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  private List<String> getValidationErrors(final MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
  }
}
