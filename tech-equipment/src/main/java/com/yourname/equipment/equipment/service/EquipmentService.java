package com.yourname.equipment.equipment.service;

import com.yourname.equipment.common.exception.BusinessException;
import com.yourname.equipment.common.exception.ResourceNotFoundException;
import com.yourname.equipment.company.entity.Company;
import com.yourname.equipment.company.repository.CompanyRepository;
import com.yourname.equipment.equipment.dto.*;
import com.yourname.equipment.equipment.entity.Equipment;
import com.yourname.equipment.equipment.entity.EquipmentAssignment;
import com.yourname.equipment.equipment.entity.EquipmentRepair;
import com.yourname.equipment.equipment.entity.EquipmentUpgrade;
import com.yourname.equipment.equipment.mapper.EquipmentAssignmentMapper;
import com.yourname.equipment.equipment.mapper.EquipmentMapper;
import com.yourname.equipment.equipment.mapper.EquipmentRepairMapper;
import com.yourname.equipment.equipment.mapper.EquipmentUpgradeMapper;
import com.yourname.equipment.equipment.repository.*;
import com.yourname.equipment.user.entity.User;
import com.yourname.equipment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с техникой.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CompanyRepository companyRepository;
    private final EquipmentCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EquipmentAssignmentRepository assignmentRepository;
    private final EquipmentRepairRepository repairRepository;
    private final EquipmentUpgradeRepository upgradeRepository;
    private final EquipmentMapper equipmentMapper;
    private final EquipmentAssignmentMapper assignmentMapper;
    private final EquipmentRepairMapper repairMapper;
    private final EquipmentUpgradeMapper upgradeMapper;

    /**
     * Создать новую единицу техники.
     */
    public EquipmentResponseDto create(CreateEquipmentDto dto) {
        log.debug("Создание техники: serialNumber={}", dto.getSerialNumber());
        
        if (equipmentRepository.existsBySerialNumber(dto.getSerialNumber())) {
            throw new BusinessException("Техника с серийным номером '" + dto.getSerialNumber() + "' уже существует");
        }

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));

        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Категория техники", dto.getCategoryId()));

        Equipment equipment = equipmentMapper.toEntity(dto);
        equipment.setCompany(company);
        equipment.setCategory(category);

        if (dto.getCurrentUserId() != null) {
            User user = userRepository.findById(dto.getCurrentUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь", dto.getCurrentUserId()));
            equipment.setCurrentUser(user);
        }

        if (equipment.getStatus() == null) {
            equipment.setStatus(Equipment.EquipmentStatus.AVAILABLE);
        }
        if (equipment.getConditionStatus() == null) {
            equipment.setConditionStatus(Equipment.ConditionStatus.GOOD);
        }

        Equipment saved = equipmentRepository.save(equipment);
        log.info("Техника создана: id={}, serialNumber={}", saved.getId(), saved.getSerialNumber());
        
        return equipmentMapper.toDto(saved);
    }

    /**
     * Получить технику по ID.
     */
    @Transactional(readOnly = true)
    public EquipmentResponseDto getById(Long id) {
        log.debug("Получение техники: id={}", id);
        
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", id));
        
        return equipmentMapper.toDto(equipment);
    }

    /**
     * Получить всю технику с фильтрами и пагинацией.
     */
    @Transactional(readOnly = true)
    public Page<EquipmentResponseDto> getAll(Long companyId, Equipment.EquipmentStatus status, 
                                             Long categoryId, Pageable pageable) {
        log.debug("Получение техники: companyId={}, status={}, categoryId={}", companyId, status, categoryId);
        
        Page<Equipment> equipment;
        if (companyId != null) {
            equipment = equipmentRepository.findByCompanyIdAndFilters(companyId, status, categoryId, pageable);
        } else if (status != null) {
            equipment = equipmentRepository.findByStatus(status, pageable);
        } else if (categoryId != null) {
            equipment = equipmentRepository.findByCategoryId(categoryId, pageable);
        } else {
            equipment = equipmentRepository.findAll(pageable);
        }
        
        return equipment.map(equipmentMapper::toDto);
    }

    /**
     * Обновить технику.
     */
    public EquipmentResponseDto update(Long id, UpdateEquipmentDto dto) {
        log.debug("Обновление техники: id={}", id);
        
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", id));

        if (dto.getSerialNumber() != null && !dto.getSerialNumber().equals(equipment.getSerialNumber())) {
            if (equipmentRepository.existsBySerialNumber(dto.getSerialNumber())) {
                throw new BusinessException("Техника с серийным номером '" + dto.getSerialNumber() + "' уже существует");
            }
        }

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Компания", dto.getCompanyId()));
            equipment.setCompany(company);
        }

        if (dto.getCategoryId() != null) {
            var category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Категория техники", dto.getCategoryId()));
            equipment.setCategory(category);
        }

        if (dto.getCurrentUserId() != null) {
            User user = userRepository.findById(dto.getCurrentUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь", dto.getCurrentUserId()));
            equipment.setCurrentUser(user);
        }

        equipmentMapper.updateEntity(equipment, dto);
        Equipment saved = equipmentRepository.save(equipment);
        log.info("Техника обновлена: id={}", saved.getId());
        
        return equipmentMapper.toDto(saved);
    }

    /**
     * Удалить технику.
     */
    public void delete(Long id) {
        log.debug("Удаление техники: id={}", id);
        
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", id));
        
        equipmentRepository.delete(equipment);
        log.info("Техника удалена: id={}", id);
    }

    /**
     * Назначить технику пользователю.
     */
    public EquipmentAssignmentResponseDto assignEquipment(Long equipmentId, AssignEquipmentDto dto) {
        log.debug("Назначение техники: equipmentId={}, userId={}", equipmentId, dto.getUserId());
        
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", equipmentId));

        if (equipment.getStatus() != Equipment.EquipmentStatus.AVAILABLE) {
            throw new BusinessException("Техника не доступна для назначения. Текущий статус: " + equipment.getStatus());
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь", dto.getUserId()));

        // Закрываем предыдущее активное назначение, если есть
        assignmentRepository.findActiveAssignmentByEquipmentId(equipmentId)
                .ifPresent(assignment -> {
                    assignment.setReturnedAt(LocalDateTime.now());
                    assignmentRepository.save(assignment);
                });

        EquipmentAssignment assignment = EquipmentAssignment.builder()
                .equipment(equipment)
                .user(user)
                .assignedAt(LocalDateTime.now())
                .notes(dto.getNotes())
                .build();

        if (dto.getDepartmentId() != null) {
            var department = user.getDepartment();
            if (department != null) {
                assignment.setDepartment(department);
            }
        }

        equipment.setCurrentUser(user);
        equipment.setStatus(Equipment.EquipmentStatus.IN_USE);

        EquipmentAssignment saved = assignmentRepository.save(assignment);
        equipmentRepository.save(equipment);
        
        log.info("Техника назначена: equipmentId={}, userId={}", equipmentId, dto.getUserId());
        
        return assignmentMapper.toDto(saved);
    }

    /**
     * Вернуть технику.
     */
    public void returnEquipment(Long equipmentId) {
        log.debug("Возврат техники: equipmentId={}", equipmentId);
        
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", equipmentId));

        EquipmentAssignment activeAssignment = assignmentRepository.findActiveAssignmentByEquipmentId(equipmentId)
                .orElseThrow(() -> new BusinessException("Активное назначение для данной техники не найдено"));

        activeAssignment.setReturnedAt(LocalDateTime.now());
        assignmentRepository.save(activeAssignment);

        equipment.setCurrentUser(null);
        equipment.setStatus(Equipment.EquipmentStatus.AVAILABLE);
        equipmentRepository.save(equipment);
        
        log.info("Техника возвращена: equipmentId={}", equipmentId);
    }

    /**
     * Получить историю назначений техники.
     */
    @Transactional(readOnly = true)
    public List<EquipmentAssignmentResponseDto> getAssignmentHistory(Long equipmentId) {
        log.debug("Получение истории назначений: equipmentId={}", equipmentId);
        
        List<EquipmentAssignment> assignments = assignmentRepository.findAllByEquipmentIdOrderByAssignedAtDesc(equipmentId);
        return assignments.stream()
                .map(assignmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Создать запись о ремонте.
     */
    public EquipmentRepairResponseDto createRepair(Long equipmentId, CreateRepairDto dto) {
        log.debug("Создание записи о ремонте: equipmentId={}", equipmentId);
        
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", equipmentId));

        EquipmentRepair repair = repairMapper.toEntity(dto);
        repair.setEquipment(equipment);
        
        if (repair.getStatus() == null) {
            repair.setStatus(EquipmentRepair.RepairStatus.PENDING);
        }

        if (repair.getStatus() == EquipmentRepair.RepairStatus.IN_PROGRESS) {
            equipment.setStatus(Equipment.EquipmentStatus.IN_REPAIR);
            equipmentRepository.save(equipment);
        }

        EquipmentRepair saved = repairRepository.save(repair);
        log.info("Запись о ремонте создана: id={}, equipmentId={}", saved.getId(), equipmentId);
        
        return repairMapper.toDto(saved);
    }

    /**
     * Получить историю ремонтов техники.
     */
    @Transactional(readOnly = true)
    public List<EquipmentRepairResponseDto> getRepairHistory(Long equipmentId) {
        log.debug("Получение истории ремонтов: equipmentId={}", equipmentId);
        
        List<EquipmentRepair> repairs = repairRepository.findByEquipmentIdOrderByStartedAtDesc(equipmentId);
        return repairs.stream()
                .map(repairMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Создать запись об апгрейде.
     */
    public EquipmentUpgradeResponseDto createUpgrade(Long equipmentId, CreateUpgradeDto dto) {
        log.debug("Создание записи об апгрейде: equipmentId={}", equipmentId);
        
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Техника", equipmentId));

        EquipmentUpgrade upgrade = upgradeMapper.toEntity(dto);
        upgrade.setEquipment(equipment);

        EquipmentUpgrade saved = upgradeRepository.save(upgrade);
        log.info("Запись об апгрейде создана: id={}, equipmentId={}", saved.getId(), equipmentId);
        
        return upgradeMapper.toDto(saved);
    }

    /**
     * Получить историю апгрейдов техники.
     */
    @Transactional(readOnly = true)
    public List<EquipmentUpgradeResponseDto> getUpgradeHistory(Long equipmentId) {
        log.debug("Получение истории апгрейдов: equipmentId={}", equipmentId);
        
        List<EquipmentUpgrade> upgrades = upgradeRepository.findByEquipmentIdOrderByUpgradedAtDesc(equipmentId);
        return upgrades.stream()
                .map(upgradeMapper::toDto)
                .collect(Collectors.toList());
    }
}
