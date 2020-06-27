package com.kakaopay.sprinkling_money.domain.distribution;

import org.springframework.data.repository.NoRepositoryBean;

import java.time.OffsetDateTime;
import java.util.Optional;

@NoRepositoryBean
public interface DistributionRepositoryCustom {

    Optional<Distribution> findByRoomIdAndTokenValue(String roomId, String tokenValue);

    Optional<Distribution> findCurrentStatus(String roomId, String tokenValue, OffsetDateTime expireDateTime);

}
