package com.kakaopay.sprinkling_money.domain.room;

import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoin;
import com.kakaopay.sprinkling_money.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Room {

    @Id
    @Column(name = "room_id")
    private String id;

    @Column(name = "room_name")
    private String name;

    public Room(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    @OneToMany(mappedBy = "room")
    private Set<RoomJoin> roomJoins;

    public boolean isJoined(User user) {
        return roomJoins.stream()
                .anyMatch(roomJoin -> roomJoin.equalsUserId(user.getId()));
    }

}
