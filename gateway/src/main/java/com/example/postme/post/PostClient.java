package com.example.postme.post;

import com.example.postme.client.BaseClient;
import com.example.postme.post.dto.NewPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class PostClient extends BaseClient {
    private static final String API_PREFIX = "/posts";

    @Autowired
    public PostClient(@Value("${postme-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getAllForUser(String username, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", username, parameters);
    }

    public ResponseEntity<Object> get(String username, long id) {
        return get("/" + id, username);
    }

    public ResponseEntity<Object> add(String username, NewPostDto newPostDto) {
        return post("", username, newPostDto);
    }

    public ResponseEntity<Object> update(String username, long id, NewPostDto newPostDto) {
        return patch("/" + id, username, newPostDto);
    }

    public ResponseEntity<Object> delete(String username, long id) {
        return delete("/" + id, username);
    }

    public ResponseEntity<Object> getAllFollowed(String username, int from) {
        Map<String, Object> parameters = Map.of(
                "from", from
        );
        return get("/sub?from={from}", username, parameters);
    }
}
