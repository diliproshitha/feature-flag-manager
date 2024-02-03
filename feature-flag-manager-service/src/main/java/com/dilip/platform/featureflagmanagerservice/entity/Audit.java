package com.dilip.platform.featureflagmanagerservice.entity;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
public class Audit {
  @Id
  @UuidGenerator
  @Column(length = 36, nullable = false, updatable = false)
  private UUID id;

  @CreatedDate
  @Setter
  private Instant createdAt;

  @LastModifiedDate
  @Setter
  private Instant modifiedAt;
}
