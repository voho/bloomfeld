package cz.voho.bloomfeld.filter;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class DefaultBloomFilterTest {
    private List<BloomFilter<String>> filters;

    @Before
    public void setUp() {
        this.filters = new LinkedList<>();

        IntStream.rangeClosed(1, 1000).boxed().forEach(size -> {
            this.filters.add(new DefaultBloomFilter<>(size, this::hash1));
            this.filters.add(new DefaultBloomFilter<>(size, this::hash1, this::hash2));
            this.filters.add(new DefaultBloomFilter<>(size, this::hash1, this::hash2, this::hash3));
        });

        new DefaultBloomFilter<String>(
                1000,
                s -> s.hashCode(),
                s -> s.toLowerCase().hashCode(),
                s -> s.toUpperCase().hashCode()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateNumBits() {
        new DefaultBloomFilter<>(0, this::hash1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateHashFunctionCount_None() {
        new DefaultBloomFilter<String>(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateHashFunctionCount_Null() {
        new DefaultBloomFilter<String>(10, null);
    }

    @Test
    public void test() throws Exception {
        final List<String> values = Arrays.asList("", " ", "a", "A", "ba", "BA", "bA", "AB", "ab");

        for (final String value : values) {
            assertNoFiltersContain(value);
        }

        for (final String value : values) {
            addToAllFilters(value);
        }

        for (final String value : values) {
            assertAllFiltersContain(value);
        }
    }

    private void addToAllFilters(final String element) {
        filters.forEach(f -> f.add(element));
    }

    private void assertAllFiltersContain(final String element) {
        assertTrue(filters.stream().allMatch(f -> f.probablyContains(element)));
    }

    private void assertNoFiltersContain(final String element) {
        assertTrue(filters.stream().noneMatch(f -> f.probablyContains(element)));
    }

    private int hash1(final String e) {
        return e.hashCode();
    }

    private int hash2(final String e) {
        return e.toLowerCase(Locale.ROOT).hashCode();
    }

    private int hash3(final String e) {
        return e.toUpperCase(Locale.ROOT).hashCode();
    }
}