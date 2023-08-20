package com.example.postme.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(String path, String username) {
        return get(path, username, null);
    }

    protected ResponseEntity<Object> get(String path, String username, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, username, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, String username, T body) {
        return post(path, username, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, String username, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, username, parameters, body);
    }

    protected <T> ResponseEntity<Object> put(String path, String username, T body) {
        return put(path, username, null, body);
    }

    protected <T> ResponseEntity<Object> put(String path, String username, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.PUT, path, username, parameters, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, String username) {
        return patch(path, username, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, String username, T body) {
        return patch(path, username, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, String username, @Nullable Map<String, Object> parameters, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, username, parameters, body);
    }

    protected <T> ResponseEntity<Object> delete(String path, String username) {
        return delete(path, username, null);
    }

    protected <T> ResponseEntity<Object> delete(String path, String username, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.DELETE, path, username, parameters, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, String username, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(username));

        ResponseEntity<Object> postmeServerResponse;
        try {
            if (parameters != null) {
                postmeServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                postmeServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(postmeServerResponse);
    }

    private HttpHeaders defaultHeaders(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (username != null) {
            headers.set("username", username);
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
