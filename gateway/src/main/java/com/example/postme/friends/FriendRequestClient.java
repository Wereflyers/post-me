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

    public ResponseEntity<Object> getRequests(String username, int from) {
        Map<String, Object> parameters = Map.of(
                "from", from
        );
        return get("?from={from}", username, parameters);
    }

    public ResponseEntity<Object> add(String username, String followedName) {
        return post("/" + followedName, username, null);
    }

    public ResponseEntity<Object> get(String username, long requestId) {
        return get("/" + requestId, username);
    }

    public ResponseEntity<Object> apply(String username, long requestId) {
        return patch("/" + requestId + "/apply", username);
    }

    public ResponseEntity<Object> cancel(String username, long requestId) {
        return patch("/" + requestId + "/cancel", username);
    }

    public ResponseEntity<Object> unsub(String username, String followedName) {
        return delete("/" + followedName, username);
    }
}
