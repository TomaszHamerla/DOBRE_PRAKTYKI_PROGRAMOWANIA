package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    void testIsPalindrome() {
        assertTrue(Utils.isPalindrome("kajak"));
        assertTrue(Utils.isPalindrome("Kobyła ma mały bok"));
        assertFalse(Utils.isPalindrome("python"));
        assertTrue(Utils.isPalindrome(""));
        assertTrue(Utils.isPalindrome("A"));
    }

    @Test
    void testFibonacci() {
        assertEquals(0, Utils.fibonacci(0));
        assertEquals(1, Utils.fibonacci(1));
        assertEquals(5, Utils.fibonacci(5));
        assertEquals(55, Utils.fibonacci(10));
        assertThrows(IllegalArgumentException.class, () -> Utils.fibonacci(-1));
    }

    @Test
    void testCountVowels() {
        assertEquals(2, Utils.countVowels("Python"));
        assertEquals(6, Utils.countVowels("AEIOUY"));
        assertEquals(0, Utils.countVowels("bcd"));
        assertEquals(0, Utils.countVowels(""));
        assertEquals(5, Utils.countVowels("Próba żółwia"));
    }

    @Test
    void testCalculateDiscount() {
        assertEquals(80.0, Utils.calculateDiscount(100, 0.2));
        assertEquals(50.0, Utils.calculateDiscount(50, 0));
        assertEquals(0.0, Utils.calculateDiscount(200, 1));

        assertThrows(IllegalArgumentException.class, () -> Utils.calculateDiscount(100, -0.1));
        assertThrows(IllegalArgumentException.class, () -> Utils.calculateDiscount(100, 1.5));
    }

    @Test
    void testFlattenList() {
        assertEquals(List.of(1, 2, 3), Utils.flattenList(List.of(1, 2, 3)));
        assertEquals(List.of(1, 2, 3, 4, 5), Utils.flattenList(List.of(1, List.of(2, 3), List.of(4, List.of(5)))));
        assertEquals(List.of(), Utils.flattenList(List.of()));
        assertEquals(List.of(1), Utils.flattenList(List.of(List.of(List.of(1)))));
        assertEquals(List.of(1, 2, 3, 4), Utils.flattenList(List.of(1, List.of(2, List.of(3, List.of(4))))));
    }

    @Test
    void testWordFrequencies() {
        assertEquals(Map.of("to", 2, "be", 2, "or", 1, "not", 1),
                Utils.wordFrequencies("To be or not to be"));

        assertEquals(Map.of("hello", 2), Utils.wordFrequencies("Hello, hello!"));

        assertEquals(Map.of(), Utils.wordFrequencies(""));

        assertEquals(Map.of("python", 3),
                Utils.wordFrequencies("Python Python python"));

        assertEquals(
                Map.of("ala", 1, "ma", 2, "kota", 1, "a", 1, "kot", 1, "ale", 1),
                Utils.wordFrequencies("Ala ma kota, a kot ma Ale.")
        );
    }

    @Test
    void testIsPrime() {
        assertTrue(Utils.isPrime(2));
        assertTrue(Utils.isPrime(3));
        assertFalse(Utils.isPrime(4));
        assertFalse(Utils.isPrime(0));
        assertFalse(Utils.isPrime(1));
        assertTrue(Utils.isPrime(5));
        assertTrue(Utils.isPrime(97));
    }
}
