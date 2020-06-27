package com.kakaopay.sprinkling_money.infrastructure.persistence.jpa.implementation.distribution;

import com.kakaopay.sprinkling_money.domain.distribution.*;
import com.kakaopay.sprinkling_money.domain.room.QRoom;
import com.kakaopay.sprinkling_money.domain.token.QToken;
import com.kakaopay.sprinkling_money.domain.user.QUser;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public class DistributionRepositoryImpl extends QuerydslRepositorySupport implements DistributionRepositoryCustom {

    public DistributionRepositoryImpl() {
        super(Distribution.class);
    }

    @Override
    public Optional<Distribution> findByRoomIdAndTokenValue(String roomId, String tokenValue) {
        QDistribution qDistribution = QDistribution.distribution;
        QToken qToken = QToken.token;
        QUser qUser = QUser.user;
        QRoom qRoom = QRoom.room;

        return Optional.ofNullable(from(qDistribution)
                .innerJoin(qDistribution.token, qToken)
                .leftJoin(qDistribution.user, qUser)
                .leftJoin(qDistribution.room, qRoom)
                .where(qDistribution.room.id.eq(roomId))
                .where(qDistribution.token.value.eq(tokenValue))
                .fetchFirst());
    }

    @Override
    public Optional<Distribution> findCurrentStatus(String roomId, String tokenValue, OffsetDateTime expireDateTime) {
        QDistribution qDistribution = QDistribution.distribution;

        QDistributionInfo qDistributionInfo = QDistributionInfo.distributionInfo;
        QToken qToken = QToken.token;
        QUser qUser = QUser.user;
        QRoom qRoom = QRoom.room;

        return Optional.ofNullable(from(qDistribution)
                .innerJoin(qDistribution.token, qToken)
                .leftJoin(qDistribution.user, qUser)
                .leftJoin(qDistribution.room, qRoom)
                .where(qDistribution.room.id.eq(roomId))
                .where(qDistribution.token.value.eq(tokenValue))
                .where(qDistribution.executionTime.after(expireDateTime))
                .fetchFirst());
    }
}
