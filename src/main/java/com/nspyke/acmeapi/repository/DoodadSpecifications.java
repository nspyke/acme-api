package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Doodad;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for Doodad entities.
 * Provides static methods to create specifications for filtering Doodads.
 */
public class DoodadSpecifications {
    private DoodadSpecifications() {
        // Static class, no instances allowed.
    }

    /**
     * Creates a specification to filter Doodads by name containing the given string (case-insensitive).
     *
     * @param name the name to search for
     * @return a specification for the name filter, or null if name is null
     */
    public static Specification<Doodad> nameLike(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    /**
     * Creates a specification to filter Doodads by description containing the given string (case-insensitive).
     *
     * @param description the description to search for
     * @return a specification for the description filter, or null if description is null
     */
    public static Specification<Doodad> descriptionLike(String description) {
        if (description == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }
}
