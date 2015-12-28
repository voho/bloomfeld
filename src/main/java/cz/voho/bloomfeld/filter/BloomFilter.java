package cz.voho.bloomfeld.filter;

/**
 * Bloom filter interface.
 *
 * @param <E> element type
 */
public interface BloomFilter<E> {
    /**
     * Returns FALSE if the element is not in the filter.
     * Returns TRUE if the element PROBABLY is in the filter (may return false positive).
     *
     * @param element element to check
     * @return TRUE if the element is PROBABLY in the filter, FALSE otherwise
     */
    boolean probablyContains(E element);

    /**
     * Adds element into the filter.
     *
     * @param element element to be added
     */
    void add(E element);
}
