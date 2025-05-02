package com.nspyke.acmeapi.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for Widget entities.
 * Used for transferring widget data between client and server.
 */
public record WidgetDto(
        Long id,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotBlank(message = "Image is required")
        String image) {

    /**
     * Default constructor that creates a WidgetDto with null values.
     */
    public WidgetDto() {
        this(null, null, null, null);
    }

    /**
     * Constructor without id (for creation).
     *
     * @param name        the widget name
     * @param description the widget description
     * @param image       the widget image reference
     */
    public WidgetDto(String name, String description, String image) {
        this(null, name, description, image);
    }
}
