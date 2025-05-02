package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Widget entities.
 * Provides CRUD operations and custom query methods for Widget entities.
 */
@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long> {
    
    /**
     * Find widgets by name containing the given string (case-insensitive).
     *
     * @param name the name to search for
     * @return a list of widgets with names containing the given string
     */
    List<Widget> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find widgets by description containing the given string (case-insensitive).
     *
     * @param description the description to search for
     * @return a list of widgets with descriptions containing the given string
     */
    List<Widget> findByDescriptionContainingIgnoreCase(String description);
}