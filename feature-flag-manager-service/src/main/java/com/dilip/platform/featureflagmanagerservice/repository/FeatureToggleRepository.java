package com.dilip.platform.featureflagmanagerservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;

public interface FeatureToggleRepository extends JpaRepository<FeatureToggle, UUID> {

}
