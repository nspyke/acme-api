package com.nspyke.acmeapi.service.impl;

import com.nspyke.acmeapi.mapper.WidgetMapper;
import com.nspyke.acmeapi.model.dto.PageResponse;
import com.nspyke.acmeapi.model.dto.WidgetDto;
import com.nspyke.acmeapi.model.entity.Widget;
import com.nspyke.acmeapi.repository.WidgetRepository;
import com.nspyke.acmeapi.service.WidgetService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the WidgetService interface.
 */
@Service
@Transactional
public class WidgetServiceImpl implements WidgetService {

    private final WidgetRepository widgetRepository;
    private final WidgetMapper widgetMapper;

    @Autowired
    public WidgetServiceImpl(WidgetRepository widgetRepository, WidgetMapper widgetMapper) {
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
    public List<WidgetDto> getAllWidgets() {
        List<Widget> widgets = widgetRepository.findAll();
        return widgetMapper.toDtoList(widgets);
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
    public PageResponse<WidgetDto> findWidgetsPaginated(String name, String description, int page, int size) {
        // Limit page size to 100
        int pageSize = Math.min(size, 100);
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Widget> widgetPage;

        boolean hasName = name != null;
        boolean hasDescription = description != null;

        if (hasName && hasDescription) {
            widgetPage = widgetRepository.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
                    name, description, pageable);
        } else if (hasName) {
            widgetPage = widgetRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (hasDescription) {
            widgetPage = widgetRepository.findByDescriptionContainingIgnoreCase(description, pageable);
        } else {
            widgetPage = widgetRepository.findAll(pageable);
        }

        List<WidgetDto> widgetDtos = widgetMapper.toDtoList(widgetPage.getContent());
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(
                widgetPage.getNumber(),
                widgetPage.getSize(),
                widgetPage.getTotalPages(),
                widgetPage.getTotalElements());

        return new PageResponse<>(widgetDtos, pageInfo);
    }
}
