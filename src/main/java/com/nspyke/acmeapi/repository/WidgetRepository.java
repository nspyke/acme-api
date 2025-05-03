package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Widget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Widget entities.
 * Provides CRUD operations and custom query methods for Widget entities.
 */
@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long>, JpaSpecificationExecutor<Widget> {
    /**
     * Find widgets by name containing the given string (case-insensitive) with pagination.
     *
     * @param name the name to search for
     * @param pageable pagination information
     * @return a page of widgets with names containing the given string
     */
    @Query("SELECT w FROM Widget w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Widget> findByName(@Param("name") String name, Pageable pageable);

    /**
     * Find widgets by description containing the given string (case-insensitive) with pagination.
     *
     * @param description the description to search for
     * @param pageable pagination information
     * @return a page of widgets with descriptions containing the given string
     */
    @Query("SELECT w FROM Widget w WHERE LOWER(w.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Widget> findByDescription(@Param("description") String description, Pageable pageable);

    /**
     * Find widgets by name and description containing the given strings (case-insensitive) with pagination.
     *
     * @param name the name to search for
     * @param description the description to search for
     * @param pageable pagination information
     * @return a page of widgets with names and descriptions containing the given strings
     */
    @Query("SELECT w FROM Widget w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :name, '%')) "
            + "AND LOWER(w.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    Page<Widget> findByNameAndDescription(
            @Param("name") String name, @Param("description") String description, Pageable pageable);
}
