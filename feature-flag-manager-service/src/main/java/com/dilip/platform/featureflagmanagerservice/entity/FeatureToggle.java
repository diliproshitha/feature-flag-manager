package com.dilip.platform.featureflagmanagerservice.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class FeatureToggle extends Audit {

  private String displayName;

  @NotBlank(message = "Technical name is mandatory")
  private String technicalName;

  private Instant expiresOn;

  private String description;

  @NotNull(message = "Inverted is mandatory")
  private boolean inverted;

  @NotNull(message = "Status is mandatory")
  private ToggleStatus status;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
  @JoinTable(name = "CUSTOMER_FEATURE_TOGGLE_MAPPING", joinColumns = @JoinColumn(name = "feature_id"),
      inverseJoinColumns = @JoinColumn(name = "customer_id"))
  @ToString.Exclude
  private List<Customer> customers;
}
