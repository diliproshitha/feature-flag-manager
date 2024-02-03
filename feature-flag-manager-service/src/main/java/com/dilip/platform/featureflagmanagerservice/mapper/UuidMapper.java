package com.dilip.platform.featureflagmanagerservice.mapper;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidMapper {

  public List<UUID> toUuidList(final List<String> ids) {
    return ids.stream()
        .map(UUID::fromString)
        .toList();
  }
}
