package com.nspyke.acmeapi.model.dto;

import java.util.List;

/**
 * Generic response object for paginated data.
 *
 * @param <T> the type of data in the page
 */
public record PagedResponse<T>(List<T> data, PagedResponse.PageInfo page) {

    /**
     * Page information including current page, total pages, page size, and total elements.
     */
    public record PageInfo(int number, int size, int totalPages, long totalElements) {}
}
