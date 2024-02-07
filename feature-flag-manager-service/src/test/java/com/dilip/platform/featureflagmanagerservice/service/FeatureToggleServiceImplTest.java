package com.dilip.platform.featureflagmanagerservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.dilip.platform.featureflagmanagerservice.entity.Customer;
import com.dilip.platform.featureflagmanagerservice.entity.FeatureToggle;
import com.dilip.platform.featureflagmanagerservice.entity.embeddable.ToggleStatus;
import com.dilip.platform.featureflagmanagerservice.exception.ResourceNotFoundException;
import com.dilip.platform.featureflagmanagerservice.mapper.FeatureToggleMapper;
import com.dilip.platform.featureflagmanagerservice.mapper.UuidMapper;
import com.dilip.platform.featureflagmanagerservice.model.FeatureToggleDto;
import com.dilip.platform.featureflagmanagerservice.repository.CustomerRepository;
import com.dilip.platform.featureflagmanagerservice.repository.FeatureToggleRepository;
import com.dilip.platform.featureflagmanagerservice.service.impl.FeatureToggleServiceImpl;
import com.dilip.platform.featureflagmanagerservice.util.TestDataUtil;

@ExtendWith(MockitoExtension.class)
class FeatureToggleServiceImplTest {

  @Mock
  private FeatureToggleRepository featureToggleRepository;
  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private FeatureToggleMapper featureToggleMapper;
  @Mock
  private UuidMapper uuidMapper;
  @InjectMocks
  private FeatureToggleServiceImpl featureToggleService;
  @Captor
  ArgumentCaptor<FeatureToggle> featureToggleCaptor;

  @Test
  void getById_givenValidId_shouldReturnFeatureToggleDto() {
    final FeatureToggle featureToggle = Instancio.create(FeatureToggle.class);
    final FeatureToggleDto featureToggleDto = TestDataUtil.featureToggleToDto(featureToggle);
    when(featureToggleRepository.findById(featureToggle.getIdAsUuid())).thenReturn(Optional.of(featureToggle));
    when(featureToggleMapper.toDto(featureToggle)).thenReturn(featureToggleDto);

    final FeatureToggleDto returnedDto = featureToggleService.getById(featureToggle.getIdAsUuid());

    assertThat(returnedDto.getIdAsUuid()).isNotNull();
    assertThat(returnedDto.getIdAsUuid()).isEqualTo(featureToggleDto.getIdAsUuid());
    assertThat(returnedDto.getTechnicalName()).isEqualTo(featureToggleDto.getTechnicalName());
    assertThat(returnedDto.getDisplayName()).isEqualTo(featureToggleDto.getDisplayName());
    assertThat(returnedDto.getExpiresOn()).isEqualTo(featureToggleDto.getExpiresOn());
    assertThat(returnedDto.getDescription()).isEqualTo(featureToggleDto.getDescription());
    assertThat(returnedDto.isInverted()).isEqualTo(featureToggleDto.isInverted());
    assertThat(returnedDto.getStatus()).isEqualTo(featureToggleDto.getStatus());
    assertThat(returnedDto.getCustomerIds()).isEqualTo(featureToggleDto.getCustomerIds());
  }

  @Test
  void getById_givenInvalidId_shouldThrowResourceNotFoundException() {
    final UUID id = UUID.randomUUID();
    when(featureToggleRepository.findById(id)).thenReturn(Optional.empty());

    final ResourceNotFoundException ex = assertThrowsExactly(ResourceNotFoundException.class,
        () -> featureToggleService.getById(id));

    assertThat(ex.getMessage()).isEqualTo("Cannot find a FeatureToggle with id: " + id);
  }

  @Test
  void save_givenValidDto_shouldReturnFeatureToggleDto() {
    final FeatureToggleDto dto = TestDataUtil.randomFeatureToggleDto();
    final FeatureToggle featureToggle = TestDataUtil.dtoToFeatureToggle(dto);
    final List<Customer> customers = TestDataUtil.idListToCustomers(dto.getCustomerIds());
    featureToggle.setCustomers(customers);
    when(customerRepository.findAllById(any(List.class))).thenReturn(customers);
    when(featureToggleMapper.toEntity(any(FeatureToggleDto.class), any(FeatureToggle.class))).thenReturn(featureToggle);
    when(featureToggleRepository.save(any(FeatureToggle.class))).thenReturn(featureToggle);
    when(featureToggleMapper.toDto(any(FeatureToggle.class))).thenReturn(dto);

    final FeatureToggleDto returnedDto = featureToggleService.save(dto);

    assertThat(returnedDto).isEqualTo(dto);
    verify(featureToggleRepository).save(featureToggleCaptor.capture());
    assertThat(featureToggleCaptor.getValue().getCustomers()).isEqualTo(customers);
  }

  @Test
  void update_givenValidDto_shouldReturnFeatureToggleDto() {
    final FeatureToggleDto dto = TestDataUtil.randomFeatureToggleDto();
    final FeatureToggle featureToggle = TestDataUtil.dtoToFeatureToggle(dto);
    final List<Customer> customers = TestDataUtil.idListToCustomers(dto.getCustomerIds());
    featureToggle.setCustomers(customers);
    when(customerRepository.findAllById(any(List.class))).thenReturn(customers);
    when(featureToggleRepository.findById(any(UUID.class))).thenReturn(Optional.of(featureToggle));
    when(featureToggleMapper.toEntity(any(FeatureToggleDto.class), any(FeatureToggle.class))).thenReturn(featureToggle);
    when(featureToggleRepository.save(any(FeatureToggle.class))).thenReturn(featureToggle);
    when(featureToggleMapper.toDto(any(FeatureToggle.class))).thenReturn(dto);

    final FeatureToggleDto returnedDto = featureToggleService.update(dto);

    assertThat(returnedDto).isEqualTo(dto);
    verify(featureToggleRepository).save(featureToggleCaptor.capture());
    assertThat(featureToggleCaptor.getValue().getCustomers()).isEqualTo(customers);
  }

  @Test
  void update_givenInvalidId_shouldThrowResourceNotFoundException() {
    final FeatureToggleDto featureToggleDto = Instancio.create(FeatureToggleDto.class);
    final UUID id = featureToggleDto.getIdAsUuid();
    when(featureToggleRepository.findById(id)).thenReturn(Optional.empty());
    when(customerRepository.findAllById(any(List.class))).thenReturn(Collections.emptyList());

    final ResourceNotFoundException ex = assertThrowsExactly(ResourceNotFoundException.class,
        () -> featureToggleService.update(featureToggleDto));

    assertThat(ex.getMessage()).isEqualTo("Cannot find a FeatureToggle with id: " + id);
  }

  @Test
  void archive_givenValidId_shouldReturnFeatureToggleDto() {
    final UUID id = UUID.randomUUID();
    final FeatureToggle featureToggle = Instancio.create(FeatureToggle.class);
    final FeatureToggleDto dto = Instancio.create(FeatureToggleDto.class);
    when(featureToggleRepository.findById(id)).thenReturn(Optional.of(featureToggle));
    when(featureToggleRepository.save(any(FeatureToggle.class))).thenReturn(featureToggle);
    when(featureToggleMapper.toDto(featureToggle)).thenReturn(dto);

    final FeatureToggleDto returnedDto = featureToggleService.archive(id);

    assertThat(returnedDto).isEqualTo(dto);
    verify(featureToggleRepository).save(featureToggleCaptor.capture());
    assertThat(featureToggleCaptor.getValue().getStatus()).isEqualTo(ToggleStatus.ARCHIVED);
  }

  @Test
  void archive_givenInvalidId_shouldThrowResourceNotFoundException() {
    final UUID id = UUID.randomUUID();
    final ResourceNotFoundException ex = assertThrowsExactly(ResourceNotFoundException.class,
        () -> featureToggleService.archive(id));

    assertThat(ex.getMessage()).isEqualTo("Cannot find a FeatureToggle with id: " + id);
  }

  @Test
  void getByPage_givenValidPageable_shouldReturnPageOfFeatureToggleDto() {
    final Pageable pageable = PageRequest.of(0, 2);
    final List<FeatureToggle> featureToggles = Instancio.ofList(FeatureToggle.class)
        .size(pageable.getPageSize()).create();
    final List<FeatureToggleDto> featureToggleDtos = TestDataUtil.featureTogglesToDtos(featureToggles);
    final Page<FeatureToggle> entityPage = PageableExecutionUtils.getPage(featureToggles, pageable,
        featureToggles::size);
    final Page<FeatureToggleDto> dtoPage = PageableExecutionUtils.getPage(featureToggleDtos, pageable,
        featureToggleDtos::size);

    when(featureToggleRepository.findAll(pageable)).thenReturn(entityPage);
    when(featureToggleMapper.toDto(any(FeatureToggle.class))).thenReturn(featureToggleDtos.get(0),
        featureToggleDtos.get(1));

    final Page<FeatureToggleDto> returnedPage = featureToggleService.getByPage(pageable);

    assertThat(returnedPage.getContent()).isEqualTo(dtoPage.getContent());
  }
}
