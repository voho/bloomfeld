# Bloomfeld

[![Travis](https://travis-ci.org/voho/bloomfeld.svg?branch=master)](https://travis-ci.org/voho/bloomfeld) [![codecov.io](https://codecov.io/github/voho/bloomfeld/coverage.svg?branch=master)](https://codecov.io/github/voho/bloomfeld?branch=master)

Simple **Bloom Filter** implementation. 
Please [read about them](https://en.wikipedia.org/wiki/Bloom_filter) first, especially if you do not know what Bloom Filter does or why it does work the way it does.

## How-to

### Create filter

Creating Bloom Filter optimally is the trickiest part.
There are some parameters which you should calculate first, depending on your use case.
Also choosing the right hash function is difficult - you can use Murmur or other fast hashes, which are favored over MDA5 and similar.

For example, lets say we want to store 1000 strings and have a false positivity probability of 0.1 (10%).
According to the results obtained from calculation utility ([BloomFilterCalculations](https://github.com/voho/bloomfeld/blob/master/src/main/java/cz/voho/bloomfeld/filter/BloomFilterCalculations.java)), we should create a Bloom Filter with *4793* bits and *3* hash functions.
You can also use some [online calculator](https://krisives.github.io/bloom-calculator/).
For the sake of simplicity, we will just use the standard *hashCode*.
To actually get three different hash functions, we can just transform the string into lower- or upper-case.

So the filter definition would look like this:

```java
BloomFilter<String> filter = new DefaultBloomFilter<String>(
    1000,
    s -> s.hashCode(),
    s -> s.toLowerCase().hashCode(),
    s -> s.toUpperCase().hashCode()
);
```

### Query filter

To query a filter, call its *probablyContains* method.

```java
boolean contains = filter.probablyContains("hello");
```
        
As the name suggests, you should really pay attention to what the returned value means:

- *TRUE* = element is PROBABLY in the set (might introduce false positives)
- *FALSE* = element is not in the set (always correct)

In other words: you can say for certain that an element is NOT in the set, but you cannot say for sure that element REALLY IS in the set.
That is the limitation of a Bloom filter.
It still has very nice use cases though!

### Put elements in filter

Simply call the *add* method.

```java
filter.add("hello");
```

## Usage

You can include this library in your Maven project using the Jitpack service.

This has two steps. Step one, include this repository:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Step two, add this dependency (you can find the latest version in `pom.xml` file):

```
<dependency>
    <groupId>com.github.voho</groupId>
    <artifactId>bloomfeld</artifactId>
    <version>{SPECIFY_VERSION_HERE}</version>
</dependency>
```
