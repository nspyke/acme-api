package com.nspyke.acmeapi.mapper;

import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.entity.Doodad;
import com.nspyke.acmeapi.model.entity.Widget;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Doodad entity and DoodadDto.
 */
@Component
public class DoodadMapper {

    /**
     * Converts a Doodad entity to a DoodadDto.
     *
     * @param doodad the Doodad entity to convert
     * @return the corresponding DoodadDto
     */
    public DoodadDto toDto(Doodad doodad) {
        if (doodad == null) {
            return null;
        }
        return new DoodadDto(
                doodad.getId(),
                doodad.getName(),
                doodad.getDescription(),
                doodad.getImage(),
                doodad.getPrice(),
                doodad.getWidget() != null ? doodad.getWidget().getId() : null);
    }

    /**
     * Converts a DoodadDto to a Doodad entity.
     *
     * @param doodadDto the DoodadDto to convert
     * @return the corresponding Doodad entity
     */
    public Doodad toEntity(DoodadDto doodadDto) {
        if (doodadDto == null) {
            return null;
        }

        Doodad doodad = new Doodad();
        doodad.setId(doodadDto.id());
        doodad.setName(doodadDto.name());
        doodad.setDescription(doodadDto.description());
        doodad.setImage(doodadDto.image());
        doodad.setPrice(doodadDto.price());

        if (doodadDto.widgetId() != null) {
            Widget widget = new Widget();
            widget.setId(doodadDto.widgetId());
            doodad.setWidget(widget);
        }

        return doodad;
    }

    /**
     * Updates an existing Doodad entity with data from a DoodadDto.
     *
     * @param doodad the Doodad entity to update
     * @param doodadDto the DoodadDto containing the new data
     * @return the updated Doodad entity
     */
    public Doodad updateEntityFromDto(Doodad doodad, DoodadDto doodadDto) {
        if (doodad == null || doodadDto == null) {
            return doodad;
        }

        // Only update non-null fields
        if (doodadDto.name() != null) {
            doodad.setName(doodadDto.name());
        }
        if (doodadDto.description() != null) {
            doodad.setDescription(doodadDto.description());
        }
        if (doodadDto.image() != null) {
            doodad.setImage(doodadDto.image());
        }
        if (doodadDto.price() != null) {
            doodad.setPrice(doodadDto.price());
        }
        if (doodadDto.widgetId() != null) {
            if (doodad.getWidget() == null) {
                doodad.setWidget(new Widget());
            }
            doodad.getWidget().setId(doodadDto.widgetId());
        }

        return doodad;
    }

    /**
     * Converts a list of Doodad entities to a list of DoodadDtos.
     *
     * @param doodads the list of Doodad entities to convert
     * @return the corresponding list of DoodadDtos
     */
    public List<DoodadDto> toDtoList(List<Doodad> doodads) {
        if (doodads == null) {
            return List.of();
        }
        return doodads.stream().map(this::toDto).collect(Collectors.toList());
    }
}
