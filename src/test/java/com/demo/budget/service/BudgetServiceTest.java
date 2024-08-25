package com.demo.budget.service;

import com.demo.budget.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BudgetServiceTest {

    @Mock
    private ExecutionContextFactory executionContextFactory;

    @Mock
    private CommandExecutor<TestCommand, String> commandExecutor;

    @Mock
    private QueryExecutor<TestQuery, String> queryExecutor;

    @Mock
    private EventExecutor<TestEvent> eventExecutor;

    @InjectMocks
    private BudgetService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteCommand_Success() {
        // Arrange
        TestCommand command = new TestCommand();
        when(executionContextFactory.getCommandExecutor(TestCommand.class))
                .thenAnswer(invocation -> commandExecutor);
        when(commandExecutor.execute(command)).thenReturn("Success");

        // Act
        String result = userService.executeCommand(command);

        // Assert
        assertEquals("Success", result);
        verify(executionContextFactory).getCommandExecutor(TestCommand.class);
        verify(commandExecutor).execute(command);
    }

    @Test
    void testExecuteCommand_NoExecutorFound() {
        // Arrange
        TestCommand command = new TestCommand();
        when(executionContextFactory.getCommandExecutor(TestCommand.class)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.executeCommand(command);
        });

        assertEquals("No executor found for command: " + command.getClass().getName(), exception.getMessage());
        verify(executionContextFactory).getCommandExecutor(TestCommand.class);
    }

    @Test
    void testExecuteQuery_Success() {
        // Arrange
        TestQuery query = new TestQuery();
        when(executionContextFactory.getQueryExecutor(TestQuery.class))
                .thenAnswer(invocation -> queryExecutor);
        when(queryExecutor.execute(query)).thenReturn("QueryResult");

        // Act
        String result = userService.executeQuery(query);

        // Assert
        assertEquals("QueryResult", result);
        verify(executionContextFactory).getQueryExecutor(TestQuery.class);
        verify(queryExecutor).execute(query);
    }

    @Test
    void testExecuteQuery_NoExecutorFound() {
        // Arrange
        TestQuery query = new TestQuery();
        when(executionContextFactory.getQueryExecutor(TestQuery.class)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.executeQuery(query);
        });

        assertEquals("No executor found for query: " + query.getClass().getName(), exception.getMessage());
        verify(executionContextFactory).getQueryExecutor(TestQuery.class);
    }

    @Test
    void testHandleEvent_Success() {
        // Arrange
        TestEvent event = new TestEvent();
        when(executionContextFactory.getEventExecutor(TestEvent.class))
                .thenAnswer(invocation -> eventExecutor);

        // Act
        userService.handleEvent(event);

        // Assert
        verify(executionContextFactory).getEventExecutor(TestEvent.class);
        verify(eventExecutor).execute(event);
    }

    @Test
    void testHandleEvent_NoExecutorFound() {
        // Arrange
        TestEvent event = new TestEvent();
        when(executionContextFactory.getEventExecutor(TestEvent.class)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.handleEvent(event);
        });

        assertEquals("No executor found for event: " + event.getClass().getName(), exception.getMessage());
        verify(executionContextFactory).getEventExecutor(TestEvent.class);
    }

    // Mock classes for testing purposes
    static class TestCommand implements Command {}
    static class TestQuery implements Query {}
    static class TestEvent implements Event {}
}