package com.nspyke.acmeapi.service;

import com.nspyke.acmeapi.model.dto.WidgetDto;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Widget operations.
 */
public interface WidgetService {

    /**
     * Create a new widget.
     *
     * @param widgetDto the widget data to create
     * @return the created widget with generated ID
     */
    WidgetDto createWidget(WidgetDto widgetDto);

    /**
     * Get all widgets.
     *
     * @return a list of all widgets
     */
    List<WidgetDto> getAllWidgets();

    /**
     * Get a widget by its ID.
     *
     * @param id the widget ID
     * @return an Optional containing the widget if found, or empty if not found
     */
    Optional<WidgetDto> getWidgetById(Long id);

    /**
     * Update an existing widget.
     *
     * @param id the ID of the widget to update
     * @param widgetDto the new widget data
     * @return an Optional containing the updated widget if found, or empty if not found
     */
    Optional<WidgetDto> updateWidget(Long id, WidgetDto widgetDto);

    /**
     * Find widgets based on optional filter parameters.
     *
     * @param name optional name filter
     * @param description optional description filter
     * @return list of widgets matching the query criteria
     */
    List<WidgetDto> findWidgets(String name, String description);
}
