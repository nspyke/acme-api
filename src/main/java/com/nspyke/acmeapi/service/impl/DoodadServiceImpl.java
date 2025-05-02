package com.nspyke.acmeapi.service.impl;

import com.nspyke.acmeapi.mapper.DoodadMapper;
import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.entity.Doodad;
import com.nspyke.acmeapi.repository.DoodadRepository;
import com.nspyke.acmeapi.service.DoodadService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<DoodadDto> findDoodads(String name, String description) {
        boolean hasName = name != null;
        boolean hasDescription = description != null;

        if (hasName && hasDescription) {
            return findDoodadsByNameAndDescription(name, description);
        }
        if (hasName) {
            return findDoodadsByName(name);
        }
        if (hasDescription) {
            return findDoodadsByDescription(description);
        }
        return getAllDoodads();
    }

    private List<DoodadDto> findDoodadsByName(String name) {
        List<Doodad> doodads = doodadRepository.findByNameContainingIgnoreCase(name);
        return doodadMapper.toDtoList(doodads);
    }

    private List<DoodadDto> findDoodadsByDescription(String description) {
        List<Doodad> doodads = doodadRepository.findByDescriptionContainingIgnoreCase(description);
        return doodadMapper.toDtoList(doodads);
    }

    private List<DoodadDto> findDoodadsByNameAndDescription(String name, String description) {
        List<Doodad> doodads =
                doodadRepository.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(name, description);
        return doodadMapper.toDtoList(doodads);
    }
}
