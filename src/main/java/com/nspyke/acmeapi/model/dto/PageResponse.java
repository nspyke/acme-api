package com.nspyke.acmeapi.model.dto;

import java.util.List;

/**
 * Generic response object for paginated data.
 *
 * @param <T> the type of data in the page
 */
public class PageResponse<T> {
    private final List<T> data;
    private final PageInfo page;

    public PageResponse(List<T> data, PageInfo page) {
        this.data = data;
        this.page = page;
    }

    public List<T> getData() {
        return data;
    }

    public PageInfo getPage() {
        return page;
    }

    /**
     * Page information including current page, total pages, page size, and total elements.
     */
    public static class PageInfo {
        private final int number;
        private final int size;
        private final int totalPages;
        private final long totalElements;

        public PageInfo(int number, int size, int totalPages, long totalElements) {
            this.number = number;
            this.size = size;
            this.totalPages = totalPages;
            this.totalElements = totalElements;
        }

        public int getNumber() {
            return number;
        }

        public int getSize() {
            return size;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public long getTotalElements() {
            return totalElements;
        }
    }
}
