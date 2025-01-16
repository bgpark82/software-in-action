package com.bgpark.exception;

import java.io.IOException;
import java.net.http.HttpClient;

public class ApiExceptionV1 {

    private static final int MAX_RETRIES = 3;

    public void makeApi(String url, int maxRetries) {
        int retries = 0;
        while (retries < maxRetries) {
            try {
                apiCall();
                break; // break loop when it success
            } catch (IOException e) {
                retries++;
                if (retries >= MAX_RETRIES) {
                    // logging for debug
                    System.out.println("Exceed max retries: %s".formatted(retries));
                    break;
                }
                try {
                    // exponent backoff
                    Thread.sleep((long) Math.pow(2, retries) * 1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }

            }
        }
    }

    private void apiCall() throws IOException{

    }

    public void makeApiRequestWithRetry(String url, int maxRetries) {
        int retries = 0;
        while (retries < maxRetries) {
            try {
                // 실제 API 요청
                break; // 성공 시 루프 종료
            } catch (Exception e) {
                retries++;
                if (retries >= maxRetries) {
                    System.out.println("최대 재시도 횟수 초과: " + e.getMessage());
                    break;
                }
                // 지수 백오프 대기
                try {
                    Thread.sleep((long) Math.pow(2, retries) * 1000); // 1초, 2초, 4초...
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
