package com.dilip.platform.featureflagmanagerservice.util;


import java.util.List;
import java.util.UUID;

import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.generators.Generators;

import com.dilip.platform.featureflagmanagerservice.entity.Audit;
import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;

public class InstancioUtil {

  public static FeatureToggleDto featureToggleToDto(final FeatureToggle featureToggle) {
    return Instancio.of(FeatureToggleDto.class)
        .set(Select.field("id"), featureToggle.getIdAsUuid())
        .set(Select.field("displayName"), featureToggle.getDisplayName())
        .set(Select.field("technicalName"), featureToggle.getTechnicalName())
        .set(Select.field("expiresOn"), featureToggle.getExpiresOn())
        .set(Select.field("description"), featureToggle.getDescription())
        .set(Select.field("inverted"), featureToggle.isInverted())
        .set(Select.field("status"), featureToggle.getStatus())
        .set(Select.field("customerIds"), toStringIds(featureToggle.getCustomers()))
        .set(Select.field("createdAt"), featureToggle.getCreatedAt())
        .set(Select.field("modifiedAt"), featureToggle.getModifiedAt())
        .create();
  }

  public static FeatureToggle dtoToFeatureToggle(final FeatureToggleDto dto) {
    return Instancio.of(FeatureToggle.class)
        .set(Select.field("id"), dto.getIdAsUuid())
        .set(Select.field("displayName"), dto.getDisplayName())
        .set(Select.field("technicalName"), dto.getTechnicalName())
        .set(Select.field("expiresOn"), dto.getExpiresOn())
        .set(Select.field("description"), dto.getDescription())
        .set(Select.field("inverted"), dto.isInverted())
        .set(Select.field("status"), dto.getStatus())
        .set(Select.field("customerIds"), idListToCustomers(dto.getCustomerIds()))
        .set(Select.field("createdAt"), dto.getCreatedAt())
        .set(Select.field("modifiedAt"), dto.getModifiedAt())
        .create();
  }

  private static List<Customer> idListToCustomers(final List<String> ids) {
    return ids.stream()
        .map(InstancioUtil::idToCustomer)
        .toList();
  }

  private static Customer idToCustomer(final String id) {
    return Instancio.of(Customer.class)
        .set(Select.field("id"), UUID.fromString(id))
        .generate(Select.field("firstName"), Generators::string)
        .generate(Select.field("lastName"), Generators::string)
        .create();
  }

  private static List<String> toStringIds(final List<? extends Audit> audits) {
    return audits.stream()
        .map(Audit::getId)
        .toList();
  }

}
