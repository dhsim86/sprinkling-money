package com.kakaopay.sprinkling_money.domain.token;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface TokenRepositoryCustom {

    @Transactional
    int saveIfNoExists(Token token);

    Token findByValue(String tokenValue);

}