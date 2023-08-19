package com.example.postme.user;

import com.example.postme.client.BaseClient;
import com.example.postme.user.dto.UserDto;
import com.example.postme.user.dto.UserForUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${postme-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    public ResponseEntity<Object> getUser(long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> addUser(UserDto user) {
        return post("", user);
    }

    public ResponseEntity<Object> updateUser(long userId, UserForUpdate user) {
        return patch("/" + userId, user);
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        return delete("/" + userId);
    }
}
