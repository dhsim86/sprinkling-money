package com.kakaopay.sprinkling_money.domain.room;

import com.kakaopay.sprinkling_money.domain.room.exception.RoomException;
import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RoomServiceTest {

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private RoomJoinRepository roomJoinRepository;

    @Autowired
    private RoomService roomService;

    @Test
    public void createRoomTest() {
        Room room;

        given:
        {
            doReturn(new Room("test")).when(roomRepository).save(any(Room.class));
        }

        when:
        {
            room = roomService.createRoom("test");
        }

        then:
        {
            assertThat(room).isNotNull().extracting("name").isEqualTo("test");
        }
    }

    @Test
    public void createRoomFailureTest() {
        given:
        {
            doThrow(mock(RuntimeException.class)).when(roomRepository).save(any(Room.class));
        }

        then:
        {
            assertThrows(RoomException.class, () -> roomService.createRoom("test"));
        }
    }

}
