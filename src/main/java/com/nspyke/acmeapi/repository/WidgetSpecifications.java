package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Widget;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for Widget entities.
 * Provides static methods to create specifications for filtering Widgets.
 */
public class WidgetSpecifications {
    private WidgetSpecifications() {
        // Static class, no instances allowed.
    }

    /**
     * Creates a specification to filter Widgets by name containing the given string (case-insensitive).
     *
     * @param name the name to search for
     * @return a specification for the name filter, or null if name is null
     */
    public static Specification<Widget> nameLike(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    /**
     * Creates a specification to filter Widgets by description containing the given string (case-insensitive).
     *
     * @param description the description to search for
     * @return a specification for the description filter, or null if description is null
     */
    public static Specification<Widget> descriptionLike(String description) {
        if (description == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }
}