package com.dilip.platform.featureflagmanagerservice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;

public interface FeatureToggleRepository extends JpaRepository<FeatureToggle, UUID> {

  /**
   * selects all the FeatureToggles with given technicalNames
   * JOIN FETCH is used to avoid N + 1
   * @param technicalNames
   * @return
   */
  @Query("SELECT ft from FeatureToggle ft LEFT JOIN FETCH ft.customers c where ft.technicalName IN ?1")
  List<FeatureToggle> findByTechnicalNamesIn(List<String> technicalNames);

}
