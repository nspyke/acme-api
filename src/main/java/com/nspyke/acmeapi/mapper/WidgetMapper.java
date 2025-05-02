package com.nspyke.acmeapi.mapper;

import com.nspyke.acmeapi.model.dto.WidgetDto;
import com.nspyke.acmeapi.model.entity.Widget;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Widget entity and WidgetDto.
 */
@Component
public class WidgetMapper {

    /**
     * Converts a Widget entity to a WidgetDto.
     *
     * @param widget the Widget entity to convert
     * @return the corresponding WidgetDto
     */
    public WidgetDto toDto(Widget widget) {
        if (widget == null) {
            return null;
        }
        return new WidgetDto(
                widget.getId(),
                widget.getName(),
                widget.getDescription(),
                widget.getImage()
        );
    }

    /**
     * Converts a WidgetDto to a Widget entity.
     *
     * @param widgetDto the WidgetDto to convert
     * @return the corresponding Widget entity
     */
    public Widget toEntity(WidgetDto widgetDto) {
        if (widgetDto == null) {
            return null;
        }
        
        Widget widget = new Widget();
        widget.setId(widgetDto.id());
        widget.setName(widgetDto.name());
        widget.setDescription(widgetDto.description());
        widget.setImage(widgetDto.image());
        
        return widget;
    }

    /**
     * Updates an existing Widget entity with data from a WidgetDto.
     *
     * @param widget the Widget entity to update
     * @param widgetDto the WidgetDto containing the new data
     * @return the updated Widget entity
     */
    public Widget updateEntityFromDto(Widget widget, WidgetDto widgetDto) {
        if (widget == null || widgetDto == null) {
            return widget;
        }
        
        // Only update non-null fields
        if (widgetDto.name() != null) {
            widget.setName(widgetDto.name());
        }
        if (widgetDto.description() != null) {
            widget.setDescription(widgetDto.description());
        }
        if (widgetDto.image() != null) {
            widget.setImage(widgetDto.image());
        }
        
        return widget;
    }

    /**
     * Converts a list of Widget entities to a list of WidgetDtos.
     *
     * @param widgets the list of Widget entities to convert
     * @return the corresponding list of WidgetDtos
     */
    public List<WidgetDto> toDtoList(List<Widget> widgets) {
        if (widgets == null) {
            return List.of();
        }
        return widgets.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}