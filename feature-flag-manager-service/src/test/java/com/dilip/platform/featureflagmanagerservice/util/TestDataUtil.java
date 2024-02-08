package com.dilip.platform.featureflagmanagerservice.util;

import static org.instancio.Select.field;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.instancio.Instancio;
import org.instancio.Select;
import org.instancio.generators.Generators;

import com.dilip.platform.featureflagmanagerservice.entity.Audit;
import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.model.CustomerDto;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;

public class TestDataUtil {

  public static List<FeatureToggleDto> featureTogglesToDtos(final List<FeatureToggle> dtos) {
    return dtos.stream()
        .map(TestDataUtil::featureToggleToDto)
        .toList();
  }

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
        .set(Select.field(Audit.class, "id"), dto.getIdAsUuid())
        .set(Select.field("displayName"), dto.getDisplayName())
        .set(Select.field("technicalName"), dto.getTechnicalName())
        .set(Select.field("expiresOn"), dto.getExpiresOn())
        .set(Select.field("description"), dto.getDescription())
        .set(Select.field("inverted"), dto.isInverted())
        .set(Select.field("status"), dto.getStatus())
        .set(Select.field("customers"), idListToCustomers(dto.getCustomerIds()))
        .set(Select.field(Audit.class, "createdAt"), dto.getCreatedAt())
        .set(Select.field(Audit.class, "modifiedAt"), dto.getModifiedAt())
        .create();
  }

  public static List<CustomerDto> customersToDtos(final List<Customer> customers) {
    return customers.stream()
        .map(TestDataUtil::customerToDto)
        .toList();
  }

  public static List<Customer> createCustomersWithoutAudit(final int size) {
    return IntStream.rangeClosed(1, size)
        .mapToObj(i -> createCustomerWithoutAudit())
        .toList();
  }

  private static Customer createCustomerWithoutAudit() {
    return Instancio.of(Customer.class)
        .ignore(field(Audit.class, "id"))
        .ignore(field(Audit.class, "createdAt"))
        .ignore(field(Audit.class, "modifiedAt"))
        .create();
  }

  public static List<FeatureToggle> createFeatureTogglesWithoutAudit(final int size) {
    return IntStream.rangeClosed(1, size)
        .mapToObj(i -> createFeatureToggleWithoutAudit())
        .toList();
  }

  private static FeatureToggle createFeatureToggleWithoutAudit() {
    return Instancio.of(FeatureToggle.class)
        .ignore(field(Audit.class, "id"))
        .ignore(field(Audit.class, "createdAt"))
        .ignore(field(Audit.class, "modifiedAt"))
        .create();
  }

  private static CustomerDto customerToDto(final Customer customer) {
    return Instancio.of(CustomerDto.class)
        .set(Select.field("id"), customer.getIdAsUuid())
        .set(Select.field("firstName"), customer.getFirstName())
        .set(Select.field("lastName"), customer.getLastName())
        .create();
  }

  public static List<Customer> idListToCustomers(final List<String> ids) {
    return ids.stream()
        .map(TestDataUtil::idToCustomer)
        .toList();
  }

  private static Customer idToCustomer(final String id) {
    return Instancio.of(Customer.class)
        .set(Select.field(Audit.class, "id"), UUID.fromString(id))
        .generate(Select.field("firstName"), Generators::string)
        .generate(Select.field("lastName"), Generators::string)
        .create();
  }

  private static List<String> toStringIds(final List<? extends Audit> audits) {
    return audits.stream()
        .map(Audit::getId)
        .toList();
  }

  public static FeatureToggleDto randomFeatureToggleDto() {
    final FeatureToggleDto dto = Instancio.create(FeatureToggleDto.class);
    dto.setCustomerIds(randomUuidAsStrings(2));
    return dto;
  }

  public static List<String> randomUuidAsStrings(final int size) {
    return Instancio.ofList(UUID.class).size(size).create().stream()
        .map(UUID::toString)
        .toList();
  }

}
