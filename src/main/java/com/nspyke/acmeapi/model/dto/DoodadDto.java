package com.nspyke.acmeapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for Doodad entities.
 * Used for transferring doodad data between client and server.
 */
public record DoodadDto(
        Long id,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Description is required") String description,
        @NotBlank(message = "Image is required") String image,
        @NotNull(message = "Price is required") @Positive(message = "Price must be positive") Float price,
        @NotNull(message = "Widget ID is required") Long widgetId) {

    /**
     * Default constructor that creates a DoodadDto with null values.
     */
    public DoodadDto() {
        this(null, null, null, null, null, null);
    }

    /**
     * Constructor without id (for creation).
     *
     * @param name        the doodad name
     * @param description the doodad description
     * @param image       the doodad image reference
     * @param price       the doodad price
     */
    public DoodadDto(String name, String description, String image, Float price, Long widgetId) {
        this(null, name, description, image, price, widgetId);
    }
}
