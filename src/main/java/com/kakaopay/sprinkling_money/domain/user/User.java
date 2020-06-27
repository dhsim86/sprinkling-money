package com.kakaopay.sprinkling_money.domain.user;

import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.room.Room;
import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoin;
import com.kakaopay.sprinkling_money.domain.user.exception.UserException;
import com.kakaopay.sprinkling_money.domain.user.exception.code.UserExceptionCode;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "user_name")
    private String name;

    @Embedded
    private Money money = new Money(0L);

    @OneToMany(mappedBy = "user")
    private Set<RoomJoin> roomJoins;

    public Room findRoom(String roomId) {
        return roomJoins.stream()
                .filter(roomJoin -> roomJoin.equalsRoomId(roomId))
                .map(RoomJoin::getRoom)
                .findFirst()
                .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_JOINED_ROOM, id, roomId));
    }

}
