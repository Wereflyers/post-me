package com.example.postme.friends;

import com.example.postme.friends.dto.FriendRequestDto;

import java.util.List;

public interface FriendRequestService {
    /**
     * Создать запрос.
     * @param userId           - подписчик
     * @param followedId - пользователь, на кого подписываются
     * @return FriendRequestDto
     */
    FriendRequestDto add(long userId, long followedId);

    /**
     * Получить запрос по идентификатору, доступно тому, кто отправил или получил запрос.
     * @param userId - пользователь
     * @param id - номер запроса
     * @return FriendRequestDto
     */
    FriendRequestDto get(long userId, long id);

    /**
     * Получить список запросов в друзья.
     * @param userId - получатель запросов
     * @param from - индекс первого элемента
     * @return List
     */
    List<FriendRequestDto> getRequests(long userId, int from);

    /**
     * Принять запрос в друзья от пользователя
     * @param requestId - запрос
     * @param userId - пользователь, получивший запрос
     * @return FriendRequestDto
     */
    FriendRequestDto apply(long userId, long requestId);

    /**
     * Отклонить запрос в друзья от пользователя
     * @param requestId - запрос
     * @param userId - пользователь, получивший запрос
     * @return FriendRequestDto
     */
    FriendRequestDto cancel(long userId, long requestId);

    /**
     * Отписаться. При удалении из друзей пользователь также отписывается, другой пользователь остается в подписчиках
     * @param userId - пользователь, который совершает отписку
     * @param followedId - пользователь, на кого была подписка
     */
    void unsub(long userId, long followedId);
}
