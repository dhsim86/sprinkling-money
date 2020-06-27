package com.kakaopay.sprinkling_money.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String>, RoomRepositoryCustom {
}
