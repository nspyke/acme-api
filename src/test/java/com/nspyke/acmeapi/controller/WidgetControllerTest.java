package com.nspyke.acmeapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.nspyke.acmeapi.model.dto.PagedResponse;
import com.nspyke.acmeapi.model.dto.WidgetDto;
import com.nspyke.acmeapi.model.dto.WidgetSearchCriteria;
import com.nspyke.acmeapi.service.WidgetService;
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
 * Tests for the WidgetController class.
 */
public class WidgetControllerTest {

    @Mock
    private WidgetService widgetService;

    @InjectMocks
    private WidgetController widgetController;

    private WidgetDto testWidget;
    private List<WidgetDto> testWidgets;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create test data
        testWidget = new WidgetDto(1L, "Test Widget", "A test widget", "test-image.jpg");
        WidgetDto widget2 = new WidgetDto(2L, "Another Widget", "Another test widget", "another-image.jpg");
        testWidgets = Arrays.asList(testWidget, widget2);
    }

    @Test
    public void testCreateWidget() {
        // Arrange
        WidgetDto inputWidget = new WidgetDto("Test Widget", "A test widget", "test-image.jpg");
        when(widgetService.createWidget(any(WidgetDto.class))).thenReturn(testWidget);

        // Act
        ResponseEntity<WidgetDto> response = widgetController.createWidget(inputWidget);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
        assertEquals("Test Widget", response.getBody().name());
    }

    @Test
    public void testGetAllWidgets_NoFilters() {
        // Arrange
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(0, 10, 1, 2);
        PagedResponse<WidgetDto> pagedResponse = new PagedResponse<>(testWidgets, pageInfo);
        when(widgetService.findWidgetsPaginated(new WidgetSearchCriteria(null, null, 0, 10))).thenReturn(pagedResponse);

        // Act
        ResponseEntity<PagedResponse<WidgetDto>> response = widgetController.getAllWidgets(new WidgetSearchCriteria(null, null, 0, 10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().data().size());
        assertEquals(0, response.getBody().page().number());
        assertEquals(10, response.getBody().page().size());
        assertEquals(1, response.getBody().page().totalPages());
        assertEquals(2, response.getBody().page().totalElements());
    }

    @Test
    public void testGetAllWidgets_FilterByName() {
        // Arrange
        List<WidgetDto> filteredWidgets = List.of(testWidget);
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(0, 10, 1, 1);
        PagedResponse<WidgetDto> pagedResponse = new PagedResponse<>(filteredWidgets, pageInfo);
        when(widgetService.findWidgetsPaginated(new WidgetSearchCriteria("Test", null, 0, 10))).thenReturn(pagedResponse);

        // Act
        ResponseEntity<PagedResponse<WidgetDto>> response = widgetController.getAllWidgets(new WidgetSearchCriteria("Test", null, 0, 10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        assertEquals("Test Widget", response.getBody().data().get(0).name());
        assertEquals(0, response.getBody().page().number());
        assertEquals(1, response.getBody().page().totalElements());
    }

    @Test
    public void testGetAllWidgets_FilterByDescription() {
        // Arrange
        List<WidgetDto> filteredWidgets = List.of(testWidget);
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(0, 10, 1, 1);
        PagedResponse<WidgetDto> pagedResponse = new PagedResponse<>(filteredWidgets, pageInfo);
        when(widgetService.findWidgetsPaginated(new WidgetSearchCriteria(null, "test", 0, 10))).thenReturn(pagedResponse);

        // Act
        ResponseEntity<PagedResponse<WidgetDto>> response = widgetController.getAllWidgets(new WidgetSearchCriteria(null, "test", 0, 10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        assertEquals("A test widget", response.getBody().data().get(0).description());
        assertEquals(1, response.getBody().page().totalElements());
    }

    @Test
    public void testGetAllWidgets_FilterByNameAndDescription() {
        // Arrange
        List<WidgetDto> filteredWidgets = List.of(testWidget);
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(0, 10, 1, 1);
        PagedResponse<WidgetDto> pagedResponse = new PagedResponse<>(filteredWidgets, pageInfo);
        when(widgetService.findWidgetsPaginated(new WidgetSearchCriteria("Test", "test", 0, 10))).thenReturn(pagedResponse);

        // Act
        ResponseEntity<PagedResponse<WidgetDto>> response = widgetController.getAllWidgets(new WidgetSearchCriteria("Test", "test", 0, 10));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        assertEquals("Test Widget", response.getBody().data().get(0).name());
        assertEquals("A test widget", response.getBody().data().get(0).description());
        assertEquals(1, response.getBody().page().totalElements());
    }

    @Test
    public void testGetAllWidgets_WithPagination() {
        // Arrange
        PagedResponse.PageInfo pageInfo = new PagedResponse.PageInfo(1, 1, 2, 2);
        PagedResponse<WidgetDto> pagedResponse = new PagedResponse<>(List.of(testWidgets.get(1)), pageInfo);
        when(widgetService.findWidgetsPaginated(new WidgetSearchCriteria(null, null, 1, 1))).thenReturn(pagedResponse);

        // Act
        ResponseEntity<PagedResponse<WidgetDto>> response = widgetController.getAllWidgets(new WidgetSearchCriteria(null, null, 1, 1));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        assertEquals(1, response.getBody().page().number());
        assertEquals(1, response.getBody().page().size());
        assertEquals(2, response.getBody().page().totalPages());
        assertEquals(2, response.getBody().page().totalElements());
    }

    @Test
    public void testGetWidgetById_Found() {
        // Arrange
        when(widgetService.getWidgetById(1L)).thenReturn(Optional.of(testWidget));

        // Act
        ResponseEntity<WidgetDto> response = widgetController.getWidgetById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    public void testGetWidgetById_NotFound() {
        // Arrange
        when(widgetService.getWidgetById(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<WidgetDto> response = widgetController.getWidgetById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateWidget_Found() {
        // Arrange
        WidgetDto updatedWidget = new WidgetDto(1L, "Updated Widget", "An updated test widget", "updated-image.jpg");
        when(widgetService.updateWidget(anyLong(), any(WidgetDto.class))).thenReturn(Optional.of(updatedWidget));

        // Act
        ResponseEntity<WidgetDto> response = widgetController.updateWidget(1L, updatedWidget);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Widget", response.getBody().name());
    }

    @Test
    public void testUpdateWidget_NotFound() {
        // Arrange
        WidgetDto updatedWidget = new WidgetDto(999L, "Updated Widget", "An updated test widget", "updated-image.jpg");
        when(widgetService.updateWidget(anyLong(), any(WidgetDto.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<WidgetDto> response = widgetController.updateWidget(999L, updatedWidget);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
