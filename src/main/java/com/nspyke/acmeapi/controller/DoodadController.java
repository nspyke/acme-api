package com.nspyke.acmeapi.controller;

import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.dto.DoodadSearchCriteria;
import com.nspyke.acmeapi.model.dto.PagedResponse;
import com.nspyke.acmeapi.service.DoodadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Doodad operations.
 */
@RestController
@RequestMapping("/api/v1/doodads")
public class DoodadController {

    private final DoodadService doodadService;

    @Autowired
    public DoodadController(DoodadService doodadService) {
        this.doodadService = doodadService;
    }

    /**
     * Create a new doodad.
     *
     * @param doodadDto the doodad data to create
     * @return the created doodad with generated ID
     */
    @PostMapping
    public ResponseEntity<DoodadDto> createDoodad(@Valid @RequestBody DoodadDto doodadDto) {
        DoodadDto createdDoodad = doodadService.createDoodad(doodadDto);
        return new ResponseEntity<>(createdDoodad, HttpStatus.CREATED);
    }

    /**
     * Get all doodads with optional filtering and pagination.
     *
     * @param criteria the search criteria containing filters and pagination parameters
     * @return a paginated response of doodads matching the filters
     */
    @GetMapping
    public ResponseEntity<PagedResponse<DoodadDto>> getAllDoodads(@ModelAttribute DoodadSearchCriteria criteria) {
        PagedResponse<DoodadDto> doodads = doodadService.findDoodadsPaginated(criteria);
        return ResponseEntity.ok(doodads);
    }

    /**
     * Get a doodad by its ID.
     *
     * @param id the doodad ID
     * @return the doodad if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DoodadDto> getDoodadById(@PathVariable Long id) {
        return doodadService
                .getDoodadById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update an existing doodad.
     *
     * @param id the ID of the doodad to update
     * @param doodadDto the new doodad data
     * @return the updated doodad if found, or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<DoodadDto> updateDoodad(@PathVariable Long id, @Valid @RequestBody DoodadDto doodadDto) {
        return doodadService
                .updateDoodad(id, doodadDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
