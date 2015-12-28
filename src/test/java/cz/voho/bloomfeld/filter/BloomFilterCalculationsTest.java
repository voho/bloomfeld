package cz.voho.bloomfeld.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BloomFilterCalculationsTest {
    @Test
    public void testEstimateOptimalNumberOfHashFunctions() throws Exception {
        assertEquals(1, BloomFilterCalculations.estimateOptimalNumberOfHashFunctions(10, 10));
        assertEquals(1, BloomFilterCalculations.estimateOptimalNumberOfHashFunctions(10, 100));
        assertEquals(1, BloomFilterCalculations.estimateOptimalNumberOfHashFunctions(10, 1000));
        assertEquals(20, BloomFilterCalculations.estimateOptimalNumberOfHashFunctions(144, 5));
        assertEquals(100, BloomFilterCalculations.estimateOptimalNumberOfHashFunctions(2876, 20));
    }

    @Test
    public void testEstimateNumberOfBits() throws Exception {
        assertEquals(1, BloomFilterCalculations.estimateNumberOfBits(1, 0.9));
        assertEquals(144, BloomFilterCalculations.estimateNumberOfBits(5, 0.000001));
        assertEquals(2876, BloomFilterCalculations.estimateNumberOfBits(100, 0.000001));
        assertEquals(959, BloomFilterCalculations.estimateNumberOfBits(100, 0.01));
        assertEquals(47, BloomFilterCalculations.estimateNumberOfBits(100, 0.8));
        assertEquals(1, BloomFilterCalculations.estimateNumberOfBits(100, 0.999999));
        assertEquals(28755147, BloomFilterCalculations.estimateNumberOfBits(999999, 0.000001));
        assertEquals(9585049, BloomFilterCalculations.estimateNumberOfBits(999999, 0.01));
    }
}