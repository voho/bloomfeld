# Bloomfeld

Simple [Bloom Filter](https://en.wikipedia.org/wiki/Bloom_filter) implementation. 
Please ready it through, if you have no idea what Bloom Filter does and why it does work the way it does.

## Usage

### Create filter

Creating Bloom Filter is the tricky part.
There are some parameters which you should calculate first, depending on your use case.
Also choosing the right hash function is difficult - you can use Murmur or other fast hashes.

For example, lets say we want to store 1000 strings and have a false positivity probability of 0.1 (10%).
According to the results obtained from calculation utility (*BloomFilterCalculations*), we should create a Bloom Filter with *4793* bits and *3* hash functions.
For the sake of simplicity, we will use the string`s *hashCode*. 
To actually get three different hash functions, we can use the string in lower-case and upper-case.

So the filter definition would look like this:

        BloomFilter<String> filter = new DefaultBloomFilter<String>(
            1000,
            s -> s.hashCode(),
            s -> s.toLowerCase().hashCode(),
            s -> s.toUpperCase().hashCode()
        );

### Query filter

To query a filter, call its *probablyContains* method.

        boolean contains = filter.probablyContains("hello");
        
As the name suggests, you should really pay attention to what the returned value means:

- *TRUE* = element is PROBABLY in the set (might introduce false positives)
- *FALSE* = element is not in the set (always correct)

In other words: you can say for certain that an element is NOT in the set, but you cannot say for sure that element REALLY IS in the set.
That is the limitation of Bloom Filter.
It still has very nice use cases though!

### Put elements in filter

Simply call the *add* method.

        filter.add("hello");

## Calculator references

* https://krisives.github.io/bloom-calculator/
* http://stackoverflow.com/questions/658439/how-many-hash-functions-does-my-bloom-filter-need