package com.dilip.platform.featureflagmanagerservice.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public class Audit {
  @Id
  @UuidGenerator
  @Column(length = 36, nullable = false, updatable = false)
  private UUID id;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant modifiedAt;

  public String getId() {
    return Objects.nonNull(id) ? id.toString() : null;
  }

  @JsonIgnore
  public UUID getIdAsUuid() {
    return id;
  }
}
