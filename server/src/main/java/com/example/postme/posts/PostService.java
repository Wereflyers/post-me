package com.example.postme.posts;

import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;

import java.util.List;

public interface PostService {

    /**
     * Получение постов данного пользователя
     * @param username - имя пользователя
     * @param from - начало выборки
     * @param size - количество объектов на странице
     * @return List
     */
    List<PostDto> getAllForUser(String username, int from, int size);

    /**
     * Добавление поста
     * @param username - имя пользователя
     * @param newPostDto - новый пост
     * @return PostDto
     */
    PostDto add(String username, NewPostDto newPostDto);

    /**
     * Получение поста
     * @param id - идентификатор поста
     * @param username - имя пользователя
     * @return PostDto
     */
    PostDto get(long id, String username);

    /**
     * Обновление поста
     * @param username - имя пользователя
     * @param id - идентификатор поста
     * @param newPostDto - новый пост
     * @return PostDto
     */
    PostDto update(String username, long id, NewPostDto newPostDto);

    /**
     * Удаление поста
     * @param username - имя пользователя
     * @param id - идентификатор поста
     */
    void delete(String username, long id);

    /**
     * Получение постов пользователей, на которых подписан аутентифицированный пользователь
     * @param username - имя пользователя
     * @param from - начало отсчета
     * @return List
     */
    List<PostDto> getAllFollowed(String username, int from);
}
