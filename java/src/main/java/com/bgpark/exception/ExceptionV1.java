package com.bgpark.exception;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class ExceptionV1 {

    public static class SharedResource {

        private Queue<Integer> q;

        public SharedResource() {
            this.q = new LinkedList();
            this.q.add(1);
        }

        public Optional<Integer> getNext() {
            return Optional.ofNullable(q.poll());
        }

        public Integer getNextV2() {
            Integer next = q.poll();
            return next == null ? -1 : next;
        }
    }
}
