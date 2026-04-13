package com.jdesprout.auth.auth_app_backend.domain.model.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void throw_exception_when_email_is_null() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> new Email(null)
                );

        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void throw_exception_when_email_is_blank() {
        assertThrows(IllegalArgumentException.class, () -> new Email("   "));
    }

    @Test
    void create_email_when_valid() {
        assertAll(
                () -> assertDoesNotThrow(() -> new Email("user@test.com")),
                () -> assertDoesNotThrow(() -> new Email("user@mail.test.com")),
                () -> assertDoesNotThrow(() -> new Email("user@test.company")),
                () -> assertDoesNotThrow(() -> new Email("user123@test123.com")),
                () -> assertDoesNotThrow(() -> new Email("first.last@test.com")),
                () -> assertDoesNotThrow(() -> new Email("first_last@test.com")),
                () -> assertDoesNotThrow(() -> new Email("first-last@test.com")),
                () -> assertDoesNotThrow(() -> new Email("first+tag@test.com")),
                () -> assertDoesNotThrow(() -> new Email("USER@TEST.COM"))
        );
    }

    @Test
    void throw_exception_when_email_has_invalid_format() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new Email(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("   ")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("usertest.com")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("user@@test.com")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("user@test")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("user@test-com")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("user@.test.com")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("user@test.com.")),
                () -> assertThrows(IllegalArgumentException.class, () -> new Email("/,.-?@test.com"))
        );
    }

}