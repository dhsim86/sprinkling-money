package com.kakaopay.sprinkling_money.domain.room.relation;

import com.kakaopay.sprinkling_money.domain.room.Room;
import com.kakaopay.sprinkling_money.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomJoin {

    @EmbeddedId
    private RoomJoinKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private Room room;

    public RoomJoin(User user, Room room) {
        id = new RoomJoinKey();
        this.user = user;
        this.room = room;
    }

    public boolean equalsRoomId(String roomId) {
        return room.getId().equals(roomId);
    }

    public boolean equalsUserId(int userId) {
        return user.getId().equals(userId);
    }

}
