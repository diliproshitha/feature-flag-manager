package com.dilip.platform.featureflagmanagerservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.util.TestDataUtil;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeatureToggleRepositoryTest {

  @Autowired
  private FeatureToggleRepository featureToggleRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Test
  void findByTechnicalNamesIn_givenTechnicalNamesAndPageable__shouldReturnAllRecordsForPage() {
    // save customers
    final List<Customer> customers = TestDataUtil.createCustomersWithoutAudit(5);
    final List<Customer> savedCustomers = new ArrayList<>();
    customers.forEach(customer -> savedCustomers.add(entityManager.persist(customer)));

    // save featureToggle
    final List<FeatureToggle> featureToggles = TestDataUtil.createFeatureTogglesWithoutAudit(3);
    featureToggles.forEach(featureToggle -> featureToggle.setCustomers(savedCustomers));
    final List<FeatureToggle> savedFeatureToggles = new ArrayList<>();
    featureToggles.forEach(featureToggle -> savedFeatureToggles.add(entityManager.persist(featureToggle)));

    // get first 3 featureToggles' technicalNames
    final List<String> technicalNames = savedFeatureToggles.subList(0, 3).stream()
        .map(FeatureToggle::getTechnicalName)
        .toList();

    final List<FeatureToggle> returnedFeatures = featureToggleRepository.findByTechnicalNamesIn(technicalNames);
    assertThat(returnedFeatures).hasSize(technicalNames.size());
    assertThat(returnedFeatures.get(0)).isEqualTo(savedFeatureToggles.get(0));
    assertThat(returnedFeatures.get(1)).isEqualTo(savedFeatureToggles.get(1));
    assertThat(returnedFeatures.get(2)).isEqualTo(savedFeatureToggles.get(2));
  }
}
