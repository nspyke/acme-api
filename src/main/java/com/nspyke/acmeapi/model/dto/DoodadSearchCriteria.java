package com.nspyke.acmeapi.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Objects;
import org.springframework.lang.Nullable;

public final class DoodadSearchCriteria {
    @Nullable
    private String name;

    @Nullable
    private String description;

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(100)
    private int size = 10;

    public DoodadSearchCriteria() {}

    public DoodadSearchCriteria(@Nullable String name, @Nullable String description, int page, int size) {
        this.name = name;
        this.description = description;
        this.page = page;
        this.size = size;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoodadSearchCriteria that = (DoodadSearchCriteria) o;
        return page == that.page
                && size == that.size
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, page, size);
    }
}
