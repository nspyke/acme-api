package com.nspyke.acmeapi.service.impl;

import com.nspyke.acmeapi.mapper.WidgetMapper;
import com.nspyke.acmeapi.model.dto.WidgetDto;
import com.nspyke.acmeapi.model.entity.Widget;
import com.nspyke.acmeapi.repository.WidgetRepository;
import com.nspyke.acmeapi.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return widgetRepository.findById(id)
                .map(widgetMapper::toDto);
    }

    @Override
    public Optional<WidgetDto> updateWidget(Long id, WidgetDto widgetDto) {
        return widgetRepository.findById(id)
                .map(existingWidget -> {
                    Widget updatedWidget = widgetMapper.updateEntityFromDto(existingWidget, widgetDto);
                    return widgetRepository.save(updatedWidget);
                })
                .map(widgetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WidgetDto> findWidgetsByName(String name) {
        List<Widget> widgets = widgetRepository.findByNameContainingIgnoreCase(name);
        return widgetMapper.toDtoList(widgets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WidgetDto> findWidgetsByDescription(String description) {
        List<Widget> widgets = widgetRepository.findByDescriptionContainingIgnoreCase(description);
        return widgetMapper.toDtoList(widgets);
    }
}