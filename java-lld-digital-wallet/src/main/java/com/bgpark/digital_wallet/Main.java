package com.bgpark.digital_wallet;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            System.out.println("number=%d".formatted(number));

            if (number == 3) {
                iterator.remove();
            }
        }

        list.stream().forEach(System.out::println);

//
//        var version = "Java 23";
//
//        System.out.println("Alice".hashCode());
//        System.out.println("Bob".hashCode());
//        class Hello {
//            private final List<String> friends;
//
//            public Hello(List<String> friends) {
//                this.friends = Collections.unmodifiableList(friends);
//            }
//
//            public List<String> getFriends() {
//                return this.friends;
//            }
//        }
//
//        List<String> friends = Arrays.asList("peter","jacob");
//        var hello = new Hello(friends);
//        hello.getFriends().stream().forEach(System.out::println);
//        for (String f : hello.getFriends()) {
//            System.out.println(f);
//        }
    }
}
