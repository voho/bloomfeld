package cz.voho.bloomfeld.filter;

import static java.lang.Math.log;

/**
 * Utility class for calculating or estimating various values related to Bloom Filters.
 */
public final class BloomFilterCalculations {
    private static final double LN_2 = log(2.0);
    private static final double LN_2_SQUARED = LN_2 * LN_2;

    private BloomFilterCalculations() {
        // NOP
    }

    /**
     * Estimates the optimal number of hash functions given the number of bits and estimated number of elements inserted.
     * The returned value will always be greater or equal to 1.
     *
     * @param numBits number of bits
     * @param estimatedElementsInserted estimated number of elements inserted
     * @return number of hash functions
     */
    public static int estimateOptimalNumberOfHashFunctions(final int numBits, final int estimatedElementsInserted) {
        if (numBits <= 0) {
            throw new IllegalArgumentException("Number of bits must be > 0.");
        }

        if (estimatedElementsInserted <= 0) {
            throw new IllegalArgumentException("Number of elements must be > 0");
        }

        return (int) Math.ceil((double) numBits / (double) estimatedElementsInserted * LN_2);
    }

    /**
     * Estimates the optimal number of bits given the estimated number of elements inserted and target false positive probability.
     * The returned value will always be greater or equal to 1.
     *
     * @param estimatedElementsInserted estimated number of elements inserted
     * @param targetFalsePositiveProbability target false positive probability
     * @return number of bits
     */
    public static int estimateNumberOfBits(final int estimatedElementsInserted, final double targetFalsePositiveProbability) {
        if (estimatedElementsInserted <= 0) {
            throw new IllegalArgumentException("Number of elements must be > 0");
        }

        if (targetFalsePositiveProbability <= 0 || targetFalsePositiveProbability >= 1.0) {
            throw new IllegalArgumentException("Target false positive probability must be > 0 and < 1.");
        }

        return (int) Math.ceil((-(double) estimatedElementsInserted * log(targetFalsePositiveProbability)) / LN_2_SQUARED);
    }
}
