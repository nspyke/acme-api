package com.nspyke.acmeapi.service.impl;

import com.nspyke.acmeapi.mapper.DoodadMapper;
import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.dto.PageResponse;
import com.nspyke.acmeapi.model.entity.Doodad;
import com.nspyke.acmeapi.repository.DoodadRepository;
import com.nspyke.acmeapi.service.DoodadService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the DoodadService interface.
 */
@Service
@Transactional
public class DoodadServiceImpl implements DoodadService {

    private final DoodadRepository doodadRepository;
    private final DoodadMapper doodadMapper;

    @Autowired
    public DoodadServiceImpl(DoodadRepository doodadRepository, DoodadMapper doodadMapper) {
        this.doodadRepository = doodadRepository;
        this.doodadMapper = doodadMapper;
    }

    @Override
    public DoodadDto createDoodad(DoodadDto doodadDto) {
        Doodad doodad = doodadMapper.toEntity(doodadDto);
        Doodad savedDoodad = doodadRepository.save(doodad);
        return doodadMapper.toDto(savedDoodad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoodadDto> getAllDoodads() {
        List<Doodad> doodads = doodadRepository.findAll();
        return doodadMapper.toDtoList(doodads);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DoodadDto> getDoodadById(Long id) {
        return doodadRepository.findById(id).map(doodadMapper::toDto);
    }

    @Override
    public Optional<DoodadDto> updateDoodad(Long id, DoodadDto doodadDto) {
        return doodadRepository
                .findById(id)
                .map(existingDoodad -> {
                    Doodad updatedDoodad = doodadMapper.updateEntityFromDto(existingDoodad, doodadDto);
                    return doodadRepository.save(updatedDoodad);
                })
                .map(doodadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DoodadDto> findDoodadsPaginated(String name, String description, int page, int size) {
        // Limit page size to 100
        int pageSize = Math.min(size, 100);
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Doodad> doodadPage;

        boolean hasName = name != null;
        boolean hasDescription = description != null;

        if (hasName && hasDescription) {
            doodadPage = doodadRepository.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
                    name, description, pageable);
        } else if (hasName) {
            doodadPage = doodadRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (hasDescription) {
            doodadPage = doodadRepository.findByDescriptionContainingIgnoreCase(description, pageable);
        } else {
            doodadPage = doodadRepository.findAll(pageable);
        }

        List<DoodadDto> doodadDtos = doodadMapper.toDtoList(doodadPage.getContent());
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                doodadPage.getNumber(),
                doodadPage.getSize(),
                doodadPage.getTotalPages(),
                doodadPage.getTotalElements());

        return new PageResponse<>(doodadDtos, pageInfo);
    }
}
