package com.nspyke.acmeapi.service;

import com.nspyke.acmeapi.model.dto.DoodadDto;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Doodad operations.
 */
public interface DoodadService {

    /**
     * Create a new doodad.
     *
     * @param doodadDto the doodad data to create
     * @return the created doodad with generated ID
     */
    DoodadDto createDoodad(DoodadDto doodadDto);

    /**
     * Get all doodads.
     *
     * @return a list of all doodads
     */
    List<DoodadDto> getAllDoodads();

    /**
     * Get a doodad by its ID.
     *
     * @param id the doodad ID
     * @return an Optional containing the doodad if found, or empty if not found
     */
    Optional<DoodadDto> getDoodadById(Long id);

    /**
     * Update an existing doodad.
     *
     * @param id the ID of the doodad to update
     * @param doodadDto the new doodad data
     * @return an Optional containing the updated doodad if found, or empty if not found
     */
    Optional<DoodadDto> updateDoodad(Long id, DoodadDto doodadDto);

    /**
     * Find doodads based on optional filter parameters.
     *
     * @param name optional name filter
     * @param description optional description filter
     * @return list of doodads matching the query criteria
     */
    List<DoodadDto> findDoodads(String name, String description);
}
