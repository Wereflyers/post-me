package com.example.postme.friends;

import com.example.postme.friends.dto.FriendRequestDto;

import java.util.List;

public interface FriendRequestService {
    /**
     * Создать запрос.
     *
     * @param username     - подписчик
     * @param followedName - пользователь, на кого подписываются
     * @return FriendRequestDto
     */
    FriendRequestDto add(String username, String followedName);

    /**
     * Получить запрос по идентификатору, доступно тому, кто отправил или получил запрос.
     * @param username - пользователь
     * @param id - номер запроса
     * @return FriendRequestDto
     */
    FriendRequestDto get(String username, long id);

    /**
     * Получить список запросов в друзья.
     * @param username - получатель запросов
     * @param from - индекс первого элемента
     * @return List
     */
    List<FriendRequestDto> getRequests(String username, int from);

    /**
     * Принять запрос в друзья от пользователя
     * @param requestId - запрос
     * @param username - пользователь, получивший запрос
     * @return FriendRequestDto
     */
    FriendRequestDto apply(String username, long requestId);

    /**
     * Отклонить запрос в друзья от пользователя
     * @param requestId - запрос
     * @param username - пользователь, получивший запрос
     * @return FriendRequestDto
     */
    FriendRequestDto cancel(String username, long requestId);

    /**
     * Отписаться. При удалении из друзей пользователь также отписывается, другой пользователь остается в подписчиках
     *
     * @param username     - пользователь, который совершает отписку
     * @param followedName - пользователь, на кого была подписка
     */
    void unsub(String username, String followedName);
}
