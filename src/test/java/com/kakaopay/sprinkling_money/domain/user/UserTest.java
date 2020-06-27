package com.kakaopay.sprinkling_money.domain.user;

import com.kakaopay.sprinkling_money.domain.room.Room;
import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoin;

import com.kakaopay.sprinkling_money.domain.user.exception.UserException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @Test
    public void findRoomTest() {
        String roomId;
        User user;

        Room result;

        given:
        {
            roomId = UUID.randomUUID().toString();

            Room room = new Room();
            room.setId(roomId);

            RoomJoin roomJoin = new RoomJoin();
            roomJoin.setRoom(room);

            user = new User();
            user.setRoomJoins(Set.of(roomJoin));
        }

        when:
        {
            result = user.findRoom(roomId);
        }

        then:
        {
            assertThat(result).isNotNull().extracting("id").isEqualTo(roomId);
        }
    }

    @Test
    public void findRoomFailureTest() {
        String roomId;
        User user;

        Room result;

        given:
        {
            roomId = UUID.randomUUID().toString();

            user = new User();
            user.setRoomJoins(Collections.emptySet());
        }

        then:
        {
            assertThrows(UserException.class, () -> user.findRoom(roomId));
        }
    }

}


