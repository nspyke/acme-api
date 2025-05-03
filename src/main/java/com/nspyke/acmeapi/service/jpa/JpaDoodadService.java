package com.nspyke.acmeapi.service.jpa;

import com.nspyke.acmeapi.mapper.DoodadMapper;
import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.dto.PagedResponse;
import com.nspyke.acmeapi.model.entity.Doodad;
import com.nspyke.acmeapi.repository.DoodadRepository;
import com.nspyke.acmeapi.repository.DoodadSpecifications;
import com.nspyke.acmeapi.service.DoodadService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA implementation of the DoodadService interface.
 */
@Service
@Transactional
public class JpaDoodadService implements DoodadService {

    private final DoodadRepository doodadRepository;
    private final DoodadMapper doodadMapper;

    @Autowired
    public JpaDoodadService(DoodadRepository doodadRepository, DoodadMapper doodadMapper) {
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
    public PagedResponse<DoodadDto> findDoodadsPaginated(String name, String description, int page, int size) {
        // Limit page size to 100
        int pageSize = Math.min(size, 100);
        Pageable pageable = PageRequest.of(page, pageSize);

        // Create specifications for name and description filters
        Specification<Doodad> nameSpec = DoodadSpecifications.nameLike(name);
        Specification<Doodad> descSpec = DoodadSpecifications.descriptionLike(description);

        // Combine specifications if both filters are provided
        Specification<Doodad> spec = Specification.where(null);

        if (nameSpec != null) {
            spec = spec.and(nameSpec);
        }

        if (descSpec != null) {
            spec = spec.and(descSpec);
        }

        // Find all doodads with specifications
        Page<Doodad> doodadPage = doodadRepository.findAll(spec, pageable);

        List<DoodadDto> doodadDtos = doodadMapper.toDtoList(doodadPage.getContent());
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(
                doodadPage.getNumber(),
                doodadPage.getSize(),
                doodadPage.getTotalPages(),
                doodadPage.getTotalElements());

        return new PagedResponse<>(doodadDtos, pageInfo);
    }
}
