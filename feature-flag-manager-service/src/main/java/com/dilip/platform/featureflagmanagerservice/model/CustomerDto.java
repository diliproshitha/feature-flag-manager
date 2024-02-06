package com.dilip.platform.featureflagmanagerservice.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CustomerDto {

  private UUID id;
  private String firstName;
  private String lastName;

  public String getId() {
    return id.toString();
  }

  @JsonIgnore
  public UUID getIdAsUUID() {
    return id;
  }
}
