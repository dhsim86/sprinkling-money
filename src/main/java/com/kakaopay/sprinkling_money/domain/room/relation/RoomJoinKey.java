package com.kakaopay.sprinkling_money.domain.room.relation;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomJoinKey implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "room_id")
    private String roomId;

}
