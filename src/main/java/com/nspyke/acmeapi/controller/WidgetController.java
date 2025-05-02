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
     * Get all widgets.
     *
     * @return a list of all widgets
     */
    @GetMapping
    public ResponseEntity<List<WidgetDto>> getAllWidgets() {
        List<WidgetDto> widgets = widgetService.getAllWidgets();
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

    /**
     * Find widgets by name (case-insensitive, partial match).
     *
     * @param name the name to search for
     * @return a list of widgets matching the name
     */
    @GetMapping("/search/byName")
    public ResponseEntity<List<WidgetDto>> findWidgetsByName(@RequestParam String name) {
        List<WidgetDto> widgets = widgetService.findWidgetsByName(name);
        return ResponseEntity.ok(widgets);
    }

    /**
     * Find widgets by description (case-insensitive, partial match).
     *
     * @param description the description to search for
     * @return a list of widgets matching the description
     */
    @GetMapping("/search/byDescription")
    public ResponseEntity<List<WidgetDto>> findWidgetsByDescription(@RequestParam String description) {
        List<WidgetDto> widgets = widgetService.findWidgetsByDescription(description);
        return ResponseEntity.ok(widgets);
    }
}
