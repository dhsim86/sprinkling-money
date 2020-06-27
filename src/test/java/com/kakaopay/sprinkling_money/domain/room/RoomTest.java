package com.kakaopay.sprinkling_money.domain.room;

import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoin;
import com.kakaopay.sprinkling_money.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {

    private User user;
    private RoomJoin roomJoin;
    private Room room;

    @BeforeEach
    public void init() {
        user = new User("test");
        user.setId(0);

        RoomJoin roomJoin = new RoomJoin();
        roomJoin.setUser(user);

        room = new Room();
        room.setRoomJoins(Set.of(roomJoin));
    }

    @Test
    public void isJoinedTest() {
        User userInput;
        boolean result;

        given:
        {
            userInput = new User("test");
            userInput.setId(0);
        }

        when:
        {
            result = room.isJoined(userInput);
        }

        then:
        {
            assertThat(result).isTrue();
        }
    }

    @Test
    public void isJoinedFalseTest() {
        User userInput;
        boolean result;

        given:
        {
            userInput = new User("test");
            userInput.setId(1);
        }

        when:
        {
            result = room.isJoined(userInput);
        }

        then:
        {
            assertThat(result).isFalse();
        }
    }


}
