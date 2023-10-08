package com.example.postme.friends;

import com.example.postme.exception.ValidationException;
import com.example.postme.exception.WrongConditionException;
import com.example.postme.friends.dto.FriendRequestDto;
import com.example.postme.user.User;
import com.example.postme.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendRequestServiceImplTest {

    @InjectMocks
    private FriendRequestServiceImpl friendRequestService;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private UserRepository userRepository;

    private FriendRequest friendRequest;
    private User user;
    @Captor
    private ArgumentCaptor<FriendRequest> requestArgumentCaptor;

    private final long requestId = 1L;

    @BeforeEach
    public void create() {
        user = new User(1L, "name", "email@mail.ru");
        friendRequest = new FriendRequest("john", "jane", RequestStatus.WAITING);
    }

    @Test
    void addTest() {
        when(userRepository.findByName("john")).thenReturn(Optional.of(user));
        when(friendRequestRepository.save(any())).thenReturn(friendRequest);

        friendRequestService.add("jane", "john");

        verify(friendRequestRepository).save(requestArgumentCaptor.capture());
        FriendRequest savedRequest = requestArgumentCaptor.getValue();

        assertEquals("jane", savedRequest.getSub());
        assertEquals("john", savedRequest.getUser());
        assertEquals(RequestStatus.WAITING, savedRequest.getStatus());
    }

    @Test
    void addExceptionTest() {
        when(userRepository.findByName("jinny")).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> friendRequestService.add("john", "jinny"));
    }

    @Test
    void getTest() {
        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.of(friendRequest));

        FriendRequestDto gotRequest = friendRequestService.get("jane", requestId);

        assertEquals("john", gotRequest.getUser().getName());
        assertEquals("jane", gotRequest.getSub().getName());
        assertEquals(RequestStatus.WAITING, gotRequest.getStatus());
    }

    @Test
    void getExceptionTest() {
        when(friendRequestRepository.findById(any())).thenReturn(Optional.of(friendRequest));

        assertThrows(NullPointerException.class, () -> friendRequestService.get("nami", requestId));
    }

    @Test
    void getRequestsTest() {
        when(friendRequestRepository.findAllBySub("jane")).thenReturn(List.of(friendRequest));

        List<FriendRequestDto> requests = friendRequestService.getRequests("jane", 0);

        assertEquals("john", requests.get(0).getUser().getName());
        assertEquals("jane", requests.get(0).getSub().getName());
        assertEquals(RequestStatus.WAITING, requests.get(0).getStatus());
    }

    @Test
    void getRequestsExceptionTest() {
        assertThrows(ValidationException.class, () -> friendRequestService.getRequests("jane", -1));
    }

    @Test
    void applyTest() {
        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.of(friendRequest));
        when(friendRequestRepository.save(any())).thenReturn(friendRequest);

        friendRequestService.apply("john", requestId);

        verify(friendRequestRepository).save(requestArgumentCaptor.capture());

        FriendRequest gotRequest = requestArgumentCaptor.getValue();

        assertEquals("john", gotRequest.getUser());
        assertEquals("jane", gotRequest.getSub());
        assertEquals(RequestStatus.ACCEPTED, gotRequest.getStatus());
    }

    @Test
    void applyExceptionTest() {
        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.of(friendRequest));

        assertThrows(NullPointerException.class, () -> friendRequestService.apply("ko", requestId));
    }

    @Test
    void cancelTest() {
        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.of(friendRequest));
        when(friendRequestRepository.save(any())).thenReturn(friendRequest);

        friendRequestService.cancel("john", requestId);

        verify(friendRequestRepository).save(requestArgumentCaptor.capture());

        FriendRequest gotRequest = requestArgumentCaptor.getValue();

        assertEquals("john", gotRequest.getUser());
        assertEquals("jane", gotRequest.getSub());
        assertEquals(RequestStatus.CANCELED, gotRequest.getStatus());
    }

    @Test
    void cancelExceptionTest() {
        when(friendRequestRepository.findById(requestId)).thenReturn(Optional.of(friendRequest));

        assertThrows(NullPointerException.class, () -> friendRequestService.cancel("ko", requestId));
    }

    @Test
    void unsubTest() {
        friendRequest.setId(requestId);
        friendRequest.setStatus(RequestStatus.ACCEPTED);

        when(userRepository.findByName(any())).thenReturn(Optional.of(new User()));
        when(friendRequestRepository.findByUserAndSub("john", "jane")).thenReturn(Optional.of(friendRequest));
        when(friendRequestRepository.findByUserAndSubAndStatus("jane", "john", RequestStatus.ACCEPTED)).thenReturn(Optional.empty());
        when(friendRequestRepository.save(any())).thenReturn(friendRequest);

        friendRequestService.unsub("jane", "john");

        verify(friendRequestRepository).save(requestArgumentCaptor.capture());

        FriendRequest request = requestArgumentCaptor.getValue();

        verify(friendRequestRepository).deleteById(requestId);
        assertEquals("jane", request.getUser());
        assertEquals("john", request.getSub());
        assertEquals(RequestStatus.CANCELED, request.getStatus());
    }

    @Test
    void unsubNullPointerExceptionTest() {
        when(userRepository.findByName(any())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> friendRequestService.unsub("name", "fName"));
    }

    @Test
    void unsubWrongConditionTest() {
        when(userRepository.findByName(any())).thenReturn(Optional.of(new User()));
        when(friendRequestRepository.findByUserAndSub("john", "jane")).thenReturn(Optional.empty());
        when(friendRequestRepository.findByUserAndSubAndStatus("jane", "john", RequestStatus.ACCEPTED)).thenReturn(Optional.empty());

        assertThrows(WrongConditionException.class, () -> friendRequestService.unsub("jane", "john"));
    }
}