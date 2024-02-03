package com.dilip.platform.featureflagmanagerservice.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilip.platform.featureflagmanagerservice.entity.Audit;

@Component
public class AuditMapper {

  public List<String> idsToStringList(final List<? extends Audit> audits) {
    return audits.stream()
        .map(audit -> audit.getId().toString())
        .toList();
  }
}
