package com.bgpark.optional;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OptionalV1Test {

    /**
     * isPresent()
     * orElse()
     * orElseGet()
     * orElseThrow()
     * ifPresent()
     * map()
     * flatMap()
     * filter()
     */

    Optional<String> hello = Optional.of("Hello");
    Optional<Object> empty = Optional.empty();

    /**
     * Check if value is present
     */
    @Test
    void isPresent() {
        assertTrue(hello.isPresent());
        assertFalse(empty.isPresent());
    }

    @Test
    void orElse() {
        assertEquals(hello.orElse("Default"), "Hello");
        assertEquals(empty.orElse("Default"), "Default");
    }

    @Test
    void orElseGet() {
        assertEquals(hello.orElseGet(() -> "Default"), "Hello");
        assertEquals(empty.orElseGet(() -> "Default"), "Default");
    }

    @Test
    void orElseThrow() {
        assertEquals(hello.orElseThrow(() -> new NoSuchElementException()), "Hello");
        assertThrows(NoSuchElementException.class, () -> empty.orElseThrow(() -> new NoSuchElementException()));
    }

    @Test
    void ifPresent() {
        hello.ifPresent(e -> assertEquals(e.toUpperCase(), "HELLO"));
    }

    /**
     * if value is present, apply the provided mapping function to it
     * Optional<Optional<T>>
     */
    @Test
    void map() {
        assertEquals(hello.map(e -> e.length()), Optional.of(5));
    }

    /**
     * if value is present, apply the provided Optional-bearing mapping function to it,
     * Optional<T>
     */
    @Test
    void flatMap() {
        assertEquals(hello.map(e -> Optional.of(e.length())), Optional.of(Optional.of(5)));
        assertEquals(hello.flatMap(e -> Optional.of(e.length())), Optional.of(5));
    }

    @Test
    void filter() {
        assertEquals(hello.filter(e -> e.length() > 4), Optional.of("Hello"));
    }
}