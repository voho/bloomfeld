package cz.voho.bloomfeld.filter;

import java.util.BitSet;
import java.util.function.Function;

/**
 * Default Bloom Filter implementation using an array of hash functions and index fix-up.
 */
public class DefaultBloomFilter<E> implements BloomFilter<E> {
    private final int numBits;
    private final BitSet bits;
    private final Function<E, Integer>[] hashFunctions;

    /**
     * Creates a new instance.
     *
     * @param numBits number of bits
     * @param hashFunctions hash functions
     * @see BloomFilterCalculations#estimateNumberOfBits(int, double)
     * @see BloomFilterCalculations#estimateOptimalNumberOfHashFunctions(int, int)
     */
    @SafeVarargs
    public DefaultBloomFilter(final int numBits, final Function<E, Integer>... hashFunctions) {
        if (numBits <= 0) {
            throw new IllegalArgumentException("Number of bits must be > 0.");
        }

        if (hashFunctions == null || hashFunctions.length < 1) {
            throw new IllegalArgumentException("At least one hash function has to be specified.");
        }

        this.numBits = numBits;
        this.bits = new BitSet(this.numBits);
        this.hashFunctions = hashFunctions;
    }

    @Override
    public boolean probablyContains(final E element) {
        for (final Function<E, Integer> hashFunction : hashFunctions) {
            final int hashValue = hashFunction.apply(element);
            final int hashBasedIndex = getSafeIndexFromHash(hashValue);

            if (!bits.get(hashBasedIndex)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void add(final E element) {
        for (final Function<E, Integer> hashFunction : hashFunctions) {
            final int hashValue = hashFunction.apply(element);
            final int hashBasedIndex = getSafeIndexFromHash(hashValue);
            bits.set(hashBasedIndex);
        }
    }

    private int getSafeIndexFromHash(final int hashValue) {
        // this prevents overflow and negative numbers
        final int index = hashValue % numBits;
        return index < 0 ? index + numBits : index;
    }
}
