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

    public ResponseEntity<Object> getAllForUser(long userId, int from, int size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> get(long userId, long id) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> add(long userId, NewPostDto newPostDto) {
        return post("", userId, newPostDto);
    }

    public ResponseEntity<Object> update(long userId, long id, NewPostDto newPostDto) {
        return patch("/" + id, userId, newPostDto);
    }

    public ResponseEntity<Object> delete(long userId, long id) {
        return delete("/" + id, userId);
    }

    public ResponseEntity<Object> getAllFollowed(long userId, int from) {
        Map<String, Object> parameters = Map.of(
                "from", from
        );
        return get("/sub?from={from}", userId, parameters);
    }
}
