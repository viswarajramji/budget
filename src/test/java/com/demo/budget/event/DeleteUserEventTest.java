package com.demo.budget.event;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserEventTest {

    @Test
    public void testNoArgsConstructor() {
        DeleteUserEvent event = new DeleteUserEvent();
        assertThat(event).isNotNull();
    }

    @Test
    public void testAllArgsConstructor() {
        DeleteUserEvent event = new DeleteUserEvent(1L);

        assertThat(event.getUserId()).isEqualTo(1L);
    }

}
