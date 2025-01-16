package com.bgpark.stream;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

/**
 * https://www.baeldung.com/java-8-streams
 */
public class StreamTest {

    @Nested
    class StreamSourceOperation {
        @Test
        void stream_empty() {
            // null 반환 방지
            Stream<Object> streamEmpty = Stream.empty();
        }

        @Test
        void stream_collection() {
            List<String> collection = Arrays.asList("a", "b", "c");
            Stream<String> streamOfCollection = collection.stream();
        }

        @Test
        void stream_array() {
            Stream<String> streamOfArray = Stream.of("a", "b", "c");
        }

        @Test
        void stream_builder() {
            Stream<String> streamBuilder = Stream.<String>builder()
                    .add("a")
                    .add("b")
                    .add("c")
                    .build();
        }

        @Test
        void stream_generate() {
            Stream<String> streamGenerate = Stream.generate(() -> "element").limit(10);
            streamGenerate.forEach(System.out::println);
        }

        @Test
        void stream_iterate() {
            Stream<Integer> streamIterate = Stream.iterate(40, n -> n + 2).limit(20);
            streamIterate.forEach(System.out::println);
        }

        // int, long, double 원시 타입 스트림 제공
        // Stream<T>는 제네릭이므로 원시 타입 제공 불가

        @Test
        void stream_primitive() {
            IntStream streamInt = IntStream.range(1, 10);
            LongStream streamLong = LongStream.range(1, 10);
            DoubleStream streamDouble = DoubleStream.iterate(1, n -> n + 1).limit(9);
            streamInt.forEach(System.out::println);
            streamLong.forEach(System.out::println);
            streamDouble.forEach(System.out::println);
        }

        @Test
        void stream_random() {
            Random random = new Random();
            DoubleStream streamRandom = random.doubles(10);
            streamRandom.forEach(System.out::println);
        }

        @Test
        void stream_string() {
            IntStream streamChars = "abc".chars();
            streamChars.forEach(System.out::println);
        }
    }

    @Nested
    class StreamIntermediateOperation {

        @Test
        void filter() {
            print(IntStream.rangeClosed(1, 10)
                    .filter(n -> n % 2 == 0)
                    .boxed());
        }

        @Test
        void map() {
            print(Stream.of("hello", "word")
                    .map(String::length));
        }

        @Test
        void flatMap() {
            print(Stream.of(Stream.of(1, 2, 3, 4, 5),
                            Stream.of(6, 7, 8, 9, 0))
                    .flatMap(stream -> stream));

            print(List.of(
                    List.of(1,2,3,4,5),
                    List.of(6,7,8,9,0))
                    .stream()
                    .flatMap(list -> list.stream())
            );
        }

        @Test
        void distinct() {
            print(Stream.of(1,1,1,1,2,2,3,3,3).distinct());
        }

        @Test
        void sorted() {
            print(Stream.of(5, 4, 2, 2, 3, 1).sorted());
            print(Stream.of(5, 4, 2, 2, 3, 1).sorted(Comparator.reverseOrder()));
            print(Stream.of("111", "22", "3").sorted(Comparator.comparing(String::length)));
            print(Stream.of("111", "22", "3").sorted(Comparator.comparing(String::length).reversed()));
        }

        @Test
        void peek() {

        }

        @Test
        void limit() {

        }

        @Test
        void skip() {

        }

        @Test
        void takeWhile() {

        }

        @Test
        void dropWhile() {

        }

        @Test
        void unordered() {

        }
    }

    private <T> void print(Stream<T> stream) {
        stream.forEach(System.out::println);
    }

    @Nested
    class StreamTerminalOperation {

    }
}
