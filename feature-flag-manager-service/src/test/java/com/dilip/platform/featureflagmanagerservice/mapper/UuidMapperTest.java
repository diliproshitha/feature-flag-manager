package com.dilip.platform.featureflagmanagerservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dilip.platform.featureflagmanagerservice.util.TestDataUtil;

@ExtendWith(MockitoExtension.class)
class UuidMapperTest {

  @InjectMocks
  private UuidMapper uuidMapper;

  @Test
  void toUuidList_givenListOfString_shouldReturnListOfUuid() {
    // given
    final List<String> uuidAsStrings = TestDataUtil.randomUuidAsStrings(2);

    // when
    final List<UUID> result = uuidMapper.toUuidList(uuidAsStrings);

    // then
    assertThat(result).isNotNull();
    assertThat(result).hasSize(uuidAsStrings.size());
    assertThat(result).containsExactly(UUID.fromString(uuidAsStrings.get(0)), UUID.fromString(uuidAsStrings.get(1)));
  }

}
