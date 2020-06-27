package com.kakaopay.sprinkling_money.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer>, TokenRepositoryCustom {
}
