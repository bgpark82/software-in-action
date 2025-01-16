package com.bgpark.exception;

import java.util.HashSet;
import java.util.Set;

public class IdempotentV1 {

    private Set<String> processRequests = new HashSet<>();

    public boolean isDuplicateRequest(String requestId) {
        // If requestId already exist, it's duplicated request
        if (processRequests.contains(requestId)) {
            return true;
        }
        // If it's not duplicated request, store it
        processRequests.add(requestId);
        return false;
    }


    // 서버에서 요청 ID를 기반으로 멱등성 처리
//    private Set<String> processedRequests = new HashSet<>();
//
//    public synchronized boolean isDuplicateRequest(String requestId) {
//        if (processedRequests.contains(requestId)) {
//            return true; // 이미 처리된 요청
//        }
//        processedRequests.add(requestId); // 처리된 요청으로 기록
//        return false;
//    }
}
