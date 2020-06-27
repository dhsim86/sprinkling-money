package com.kakaopay.sprinkling_money.domain.distribution;

import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.distribution.exception.DistributionException;
import com.kakaopay.sprinkling_money.domain.distribution.exception.code.DistributionExceptionCode;
import com.kakaopay.sprinkling_money.domain.token.Token;
import com.kakaopay.sprinkling_money.domain.user.User;
import com.kakaopay.sprinkling_money.domain.room.Room;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Distribution {

    @Id
    @Column(name = "distribution_id")
    private String id;

    private Integer targetMemberCount;

    @Embedded
    private Money money;

    private OffsetDateTime executionTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

    @OneToMany(mappedBy = "distribution", cascade = {CascadeType.ALL})
    private List<DistributionInfo> distributionInfoList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private void relateWithInfoList() {
        this.distributionInfoList.stream().forEach(distributionInfo -> distributionInfo.setDistribution(this));
    }

    public void relate(User user, Room room) {
        this.user = user;
        this.room = room;
    }

    public boolean isDistributor(User user) {
        return this.user.equals(user);
    }

    public boolean validateToken(Token token) {
        return token.equals(token);
    }

    public DistributionState validateReceivable(User user) {
        if (this.user.equals(user)) {
            return DistributionState.NOT_TARGET;
        }

        if (executionTime.isBefore(OffsetDateTime.now().minusMinutes(10))) {
            return DistributionState.EXPIRED;
        }

        if (room.isJoined(user) == false) {
            return DistributionState.NOT_TARGET;
        }

        if (distributionInfoList.stream().allMatch(info -> info.isReceived())) {
            return DistributionState.EXHAUSTED;
        }

        if (distributionInfoList.stream().anyMatch(info -> info.isReceived(user))) {
            return DistributionState.ALREADY_RECEIVED;
        }

        return DistributionState.NORMAL;
    }

    public Money distribute(User user) {
        if (validateReceivable(user).equals(DistributionState.NORMAL) == false) {
            throw new DistributionException(DistributionExceptionCode.DISTRIBUTION_NOT_RECEIVABLE, user.getId(), getId());
        }

        DistributionInfo distributionInfo = distributionInfoList.stream()
                .filter(info -> info.isReceived() == false)
                .findFirst()
                .orElseThrow(() -> new DistributionException(DistributionExceptionCode.DISTRIBUTION_NO_MORE_SHARING, getId()));

        return distributionInfo.allocate(user);
    }

    public static Distribution newInstance(int targetMemberCount, Money totalMoney, Token token, List<Money> distributedMoneyList) {
        Distribution distribution = Distribution.builder()
                .id(UUID.randomUUID().toString())
                .targetMemberCount(targetMemberCount)
                .money(totalMoney)
                .executionTime(OffsetDateTime.now())
                .distributionInfoList(distributedMoneyList.stream().map(DistributionInfo::new).collect(Collectors.toList()))
                .token(token)
                .build();
        distribution.relateWithInfoList();
        return distribution;
    }

}
