package com.example.postme.friends;

import com.example.postme.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class FriendRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public FriendRequestClient(@Value("${postme-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getRequests(long userId, int from) {
        Map<String, Object> parameters = Map.of(
                "from", from
        );
        return get("?from={from}", userId, parameters);
    }

    public ResponseEntity<Object> add(long userId, long followedId) {
        return post("/" + followedId, userId);
    }

    public ResponseEntity<Object> get(long userId, long requestId) {
        return get("/" + requestId, userId);
    }

    public ResponseEntity<Object> apply(long userId, long requestId) {
        return patch("/" + requestId + "/apply", userId);
    }

    public ResponseEntity<Object> cancel(long userId, long requestId) {
        return patch("/" + requestId + "/cancel", userId);
    }

    public ResponseEntity<Object> unsub(long userId, long followedId) {
        return delete("/" + followedId, userId);
    }
}
