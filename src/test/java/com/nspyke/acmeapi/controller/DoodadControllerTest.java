package com.nspyke.acmeapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.nspyke.acmeapi.model.dto.DoodadDto;
import com.nspyke.acmeapi.model.dto.PageResponse;
import com.nspyke.acmeapi.service.DoodadService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Tests for the DoodadController class.
 */
public class DoodadControllerTest {

    @Mock
    private DoodadService doodadService;

    @InjectMocks
    private DoodadController doodadController;

    private DoodadDto testDoodad;
    private List<DoodadDto> testDoodads;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create test data
        testDoodad = new DoodadDto(1L, "Test Doodad", "A test doodad", "test-image.jpg", 10.99F);
        DoodadDto doodad2 = new DoodadDto(2L, "Another Doodad", "Another test doodad", "another-image.jpg", 15.99F);
        testDoodads = Arrays.asList(testDoodad, doodad2);
    }

    @Test
    public void testCreateDoodad() {
        // Arrange
        DoodadDto inputDoodad = new DoodadDto("Test Doodad", "A test doodad", "test-image.jpg", 10.99F);
        when(doodadService.createDoodad(any(DoodadDto.class))).thenReturn(testDoodad);

        // Act
        ResponseEntity<DoodadDto> response = doodadController.createDoodad(inputDoodad);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        assertEquals("Test Doodad", response.getBody().name());
        assertEquals(10.99F, response.getBody().price());
    }

    @Test
    public void testGetAllDoodads_NoFilters() {
        // Arrange
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(0, 10, 1, 2);
        PageResponse<DoodadDto> pageResponse = new PageResponse<>(testDoodads, pageInfo);
        when(doodadService.findDoodadsPaginated(null, null, 0, 10)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<DoodadDto>> response = doodadController.getAllDoodads(null, null, 0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getData().size());
        assertEquals(0, response.getBody().getPage().getNumber());
        assertEquals(10, response.getBody().getPage().getSize());
        assertEquals(1, response.getBody().getPage().getTotalPages());
        assertEquals(2, response.getBody().getPage().getTotalElements());
    }

    @Test
    public void testGetAllDoodads_FilterByName() {
        // Arrange
        List<DoodadDto> filteredDoodads = List.of(testDoodad);
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(0, 10, 1, 1);
        PageResponse<DoodadDto> pageResponse = new PageResponse<>(filteredDoodads, pageInfo);
        when(doodadService.findDoodadsPaginated("Test", null, 0, 10)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<DoodadDto>> response = doodadController.getAllDoodads("Test", null, 0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Test Doodad", response.getBody().getData().get(0).name());
        assertEquals(0, response.getBody().getPage().getNumber());
        assertEquals(1, response.getBody().getPage().getTotalElements());
    }

    @Test
    public void testGetAllDoodads_FilterByDescription() {
        // Arrange
        List<DoodadDto> filteredDoodads = List.of(testDoodad);
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(0, 10, 1, 1);
        PageResponse<DoodadDto> pageResponse = new PageResponse<>(filteredDoodads, pageInfo);
        when(doodadService.findDoodadsPaginated(null, "test", 0, 10)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<DoodadDto>> response = doodadController.getAllDoodads(null, "test", 0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("A test doodad", response.getBody().getData().get(0).description());
        assertEquals(1, response.getBody().getPage().getTotalElements());
    }

    @Test
    public void testGetAllDoodads_FilterByNameAndDescription() {
        // Arrange
        List<DoodadDto> filteredDoodads = List.of(testDoodad);
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(0, 10, 1, 1);
        PageResponse<DoodadDto> pageResponse = new PageResponse<>(filteredDoodads, pageInfo);
        when(doodadService.findDoodadsPaginated("Test", "test", 0, 10)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<DoodadDto>> response = doodadController.getAllDoodads("Test", "test", 0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        assertEquals("Test Doodad", response.getBody().getData().get(0).name());
        assertEquals("A test doodad", response.getBody().getData().get(0).description());
        assertEquals(1, response.getBody().getPage().getTotalElements());
    }

    @Test
    public void testGetAllDoodads_WithPagination() {
        // Arrange
        PageResponse.PageInfo pageInfo = new PageResponse.PageInfo(1, 1, 2, 2);
        PageResponse<DoodadDto> pageResponse = new PageResponse<>(List.of(testDoodads.get(1)), pageInfo);
        when(doodadService.findDoodadsPaginated(null, null, 1, 1)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<DoodadDto>> response = doodadController.getAllDoodads(null, null, 1, 1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        assertEquals(1, response.getBody().getPage().getNumber());
        assertEquals(1, response.getBody().getPage().getSize());
        assertEquals(2, response.getBody().getPage().getTotalPages());
        assertEquals(2, response.getBody().getPage().getTotalElements());
    }

    @Test
    public void testGetDoodadById_Found() {
        // Arrange
        when(doodadService.getDoodadById(1L)).thenReturn(Optional.of(testDoodad));

        // Act
        ResponseEntity<DoodadDto> response = doodadController.getDoodadById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    public void testGetDoodadById_NotFound() {
        // Arrange
        when(doodadService.getDoodadById(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<DoodadDto> response = doodadController.getDoodadById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateDoodad_Found() {
        // Arrange
        DoodadDto updatedDoodad =
                new DoodadDto(1L, "Updated Doodad", "An updated test doodad", "updated-image.jpg", 12.99F);
        when(doodadService.updateDoodad(anyLong(), any(DoodadDto.class))).thenReturn(Optional.of(updatedDoodad));

        // Act
        ResponseEntity<DoodadDto> response = doodadController.updateDoodad(1L, updatedDoodad);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Doodad", response.getBody().name());
        assertEquals(12.99F, response.getBody().price());
    }

    @Test
    public void testUpdateDoodad_NotFound() {
        // Arrange
        DoodadDto updatedDoodad =
                new DoodadDto(999L, "Updated Doodad", "An updated test doodad", "updated-image.jpg", 12.99F);
        when(doodadService.updateDoodad(anyLong(), any(DoodadDto.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<DoodadDto> response = doodadController.updateDoodad(999L, updatedDoodad);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
