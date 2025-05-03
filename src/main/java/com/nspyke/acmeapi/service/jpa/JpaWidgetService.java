package com.nspyke.acmeapi.service.jpa;

import com.nspyke.acmeapi.mapper.WidgetMapper;
import com.nspyke.acmeapi.model.dto.PagedResponse;
import com.nspyke.acmeapi.model.dto.WidgetDto;
import com.nspyke.acmeapi.model.entity.Widget;
import com.nspyke.acmeapi.repository.WidgetRepository;
import com.nspyke.acmeapi.repository.WidgetSpecifications;
import com.nspyke.acmeapi.service.WidgetService;
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
 * JPA implementation of the WidgetService interface.
 */
@Service
@Transactional
public class JpaWidgetService implements WidgetService {

    private final WidgetRepository widgetRepository;
    private final WidgetMapper widgetMapper;

    @Autowired
    public JpaWidgetService(WidgetRepository widgetRepository, WidgetMapper widgetMapper) {
        this.widgetRepository = widgetRepository;
        this.widgetMapper = widgetMapper;
    }

    @Override
    public WidgetDto createWidget(WidgetDto widgetDto) {
        Widget widget = widgetMapper.toEntity(widgetDto);
        Widget savedWidget = widgetRepository.save(widget);
        return widgetMapper.toDto(savedWidget);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WidgetDto> getWidgetById(Long id) {
        return widgetRepository.findById(id).map(widgetMapper::toDto);
    }

    @Override
    public Optional<WidgetDto> updateWidget(Long id, WidgetDto widgetDto) {
        return widgetRepository
                .findById(id)
                .map(existingWidget -> {
                    Widget updatedWidget = widgetMapper.updateEntityFromDto(existingWidget, widgetDto);
                    return widgetRepository.save(updatedWidget);
                })
                .map(widgetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<WidgetDto> findWidgetsPaginated(String name, String description, int page, int size) {
        // Limit page size to 100
        int pageSize = Math.min(size, 100);
        Pageable pageable = PageRequest.of(page, pageSize);

        // Create specifications for name and description filters
        Specification<Widget> nameSpec = WidgetSpecifications.nameLike(name);
        Specification<Widget> descSpec = WidgetSpecifications.descriptionLike(description);

        // Combine specifications if both filters are provided
        Specification<Widget> spec = Specification.where(null);

        if (nameSpec != null) {
            spec = spec.and(nameSpec);
        }

        if (descSpec != null) {
            spec = spec.and(descSpec);
        }

        // Find all widgets with specifications
        Page<Widget> widgetPage = widgetRepository.findAll(spec, pageable);

        List<WidgetDto> widgetDtos = widgetMapper.toDtoList(widgetPage.getContent());
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(
                widgetPage.getNumber(),
                widgetPage.getSize(),
                widgetPage.getTotalPages(),
                widgetPage.getTotalElements());

        return new PagedResponse<>(widgetDtos, pageInfo);
    }
}
