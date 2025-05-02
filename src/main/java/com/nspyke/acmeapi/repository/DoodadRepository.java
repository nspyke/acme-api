package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Doodad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Doodad entities.
 * Provides CRUD operations and custom query methods for Doodad entities.
 */
@Repository
public interface DoodadRepository extends JpaRepository<Doodad, Long> {

    /**
     * Find doodads by name containing the given string (case-insensitive).
     *
     * @param name the name to search for
     * @return a list of doodads with names containing the given string
     */
    List<Doodad> findByNameContainingIgnoreCase(String name);

    /**
     * Find doodads by description containing the given string (case-insensitive).
     *
     * @param description the description to search for
     * @return a list of doodads with descriptions containing the given string
     */
    List<Doodad> findByDescriptionContainingIgnoreCase(String description);

    /**
     * Find doodads by name and description containing the given strings (case-insensitive).
     *
     * @param name the name to search for
     * @param description the description to search for
     * @return a list of doodads with names and descriptions containing the given strings
     */
    List<Doodad> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description);
}
