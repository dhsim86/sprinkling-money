package com.kakaopay.sprinkling_money.domain.room;

import com.kakaopay.sprinkling_money.domain.room.exception.RoomException;
import com.kakaopay.sprinkling_money.domain.room.exception.code.RoomExceptionCode;
import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoin;
import com.kakaopay.sprinkling_money.domain.room.relation.RoomJoinRepository;
import com.kakaopay.sprinkling_money.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomJoinRepository roomJoinRepository;

    public Room createRoom(String roomName) {
        try {
            Room room = new Room(roomName);
            return roomRepository.save(room);
        } catch (Exception ex) {
            throw new RoomException(ex, RoomExceptionCode.ROOM_CREATION_FAILED, roomName);
        }
    }

    public void joinRoom(Room room, List<User> userList) {
        List<RoomJoin> roomJoinList = userList.stream()
                .map(user -> new RoomJoin(user, room))
                .collect(Collectors.toList());

        roomJoinRepository.saveAll(roomJoinList);
    }

}
