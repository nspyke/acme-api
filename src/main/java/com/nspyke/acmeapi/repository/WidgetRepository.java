package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Widget;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

    /**
     * Find widgets by name and description containing the given strings (case-insensitive).
     *
     * @param name the name to search for
     * @param description the description to search for
     * @return a list of widgets with names and descriptions containing the given strings
     */
    List<Widget> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description);

    /**
     * Find widgets by name containing the given string (case-insensitive) with pagination.
     *
     * @param name the name to search for
     * @param pageable pagination information
     * @return a page of widgets with names containing the given string
     */
    Page<Widget> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Find widgets by description containing the given string (case-insensitive) with pagination.
     *
     * @param description the description to search for
     * @param pageable pagination information
     * @return a page of widgets with descriptions containing the given string
     */
    Page<Widget> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    /**
     * Find widgets by name and description containing the given strings (case-insensitive) with pagination.
     *
     * @param name the name to search for
     * @param description the description to search for
     * @param pageable pagination information
     * @return a page of widgets with names and descriptions containing the given strings
     */
    Page<Widget> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            String name, String description, Pageable pageable);
}
