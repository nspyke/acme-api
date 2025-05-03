package com.nspyke.acmeapi.service;

import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.dto.DoodadSearchCriteria;
import com.nspyke.acmeapi.model.dto.PagedResponse;
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
     * Find doodads based on search criteria with pagination.
     *
     * @param criteria the search criteria containing filters and pagination parameters
     * @return paginated response of doodads matching the query criteria
     */
    PagedResponse<DoodadDto> findDoodadsPaginated(DoodadSearchCriteria criteria);
}
