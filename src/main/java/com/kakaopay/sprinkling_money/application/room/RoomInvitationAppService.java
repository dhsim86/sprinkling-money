package com.kakaopay.sprinkling_money.application.room;

import com.kakaopay.sprinkling_money.domain.room.Room;
import com.kakaopay.sprinkling_money.domain.room.RoomService;
import com.kakaopay.sprinkling_money.domain.user.User;
import com.kakaopay.sprinkling_money.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RoomInvitationAppService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Transactional
    public String inviteRoom(int userId, String roomName, List<Integer> userIdList) {
        List<Integer> targetUserIdList = IntStream.concat(IntStream.of(userId),
                userIdList.stream().mapToInt(i -> i))
                .mapToObj(i -> i)
                .collect(Collectors.toList());
        List<User> targetUserList = userRepository.findAllById(targetUserIdList);

        Room room = roomService.createRoom(roomName);
        roomService.joinRoom(room, targetUserList);

        return room.getId();
    }

}
