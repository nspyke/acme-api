package com.nspyke.acmeapi.controller;

import com.nspyke.acmeapi.model.dto.WidgetDto;
import com.nspyke.acmeapi.service.WidgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Widget operations.
 */
@RestController
@RequestMapping("/api/v1/widgets")
public class WidgetController {

    private final WidgetService widgetService;

    @Autowired
    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    /**
     * Create a new widget.
     *
     * @param widgetDto the widget data to create
     * @return the created widget with generated ID
     */
    @PostMapping
    public ResponseEntity<WidgetDto> createWidget(@Valid @RequestBody WidgetDto widgetDto) {
        WidgetDto createdWidget = widgetService.createWidget(widgetDto);
        return new ResponseEntity<>(createdWidget, HttpStatus.CREATED);
    }

    /**
     * Get all widgets with optional filtering.
     *
     * @param name optional name filter (case-insensitive, partial match)
     * @param description optional description filter (case-insensitive, partial match)
     * @return a list of widgets matching the filters, or all widgets if no filters provided
     */
    @GetMapping
    public ResponseEntity<List<WidgetDto>> getAllWidgets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {

        List<WidgetDto> widgets = widgetService.findWidgets(name, description);
        return ResponseEntity.ok(widgets);
    }

    /**
     * Get a widget by its ID.
     *
     * @param id the widget ID
     * @return the widget if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<WidgetDto> getWidgetById(@PathVariable Long id) {
        return widgetService.getWidgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update an existing widget.
     *
     * @param id the ID of the widget to update
     * @param widgetDto the new widget data
     * @return the updated widget if found, or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<WidgetDto> updateWidget(@PathVariable Long id, @Valid @RequestBody WidgetDto widgetDto) {
        return widgetService.updateWidget(id, widgetDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
