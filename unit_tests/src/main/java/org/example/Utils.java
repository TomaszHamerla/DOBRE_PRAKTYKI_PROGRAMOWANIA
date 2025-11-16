package org.example;

import java.util.*;
import java.util.regex.*;

public class Utils {

    public static boolean isPalindrome(String text) {
        if (text == null) return false;
        String cleaned = text.replaceAll("\\s+", "").toLowerCase();
        return new StringBuilder(cleaned).reverse().toString().equals(cleaned);
    }

    public static int fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        if (n == 0 || n == 1) return n;

        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int next = a + b;
            a = b;
            b = next;
        }
        return b;
    }

    public static int countVowels(String text) {
        if (text == null) return 0;
        String vowels = "aeiouyąęó";
        int count = 0;

        for (char c : text.toLowerCase().toCharArray()) {
            if (vowels.indexOf(c) != -1) count++;
        }
        return count;
    }

    public static double calculateDiscount(double price, double discount) {
        if (discount < 0 || discount > 1)
            throw new IllegalArgumentException("discount must be between 0 and 1");

        return price * (1 - discount);
    }

    public static List<Object> flattenList(List<?> nested) {
        List<Object> result = new ArrayList<>();
        flatten(nested, result);
        return result;
    }

    private static void flatten(Object item, List<Object> result) {
        if (item instanceof List<?>) {
            for (Object element : (List<?>) item) {
                flatten(element, result);
            }
        } else {
            result.add(item);
        }
    }

    public static Map<String, Integer> wordFrequencies(String text) {
        Map<String, Integer> map = new HashMap<>();

        if (text == null || text.isBlank()) return map;

        String cleaned = text.replaceAll("[^\\w\\s]", "").toLowerCase();
        String[] words = cleaned.split("\\s+");

        for (String w : words) {
            map.put(w, map.getOrDefault(w, 0) + 1);
        }
        return map;
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
