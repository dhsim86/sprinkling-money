package com.kakaopay.sprinkling_money.infrastructure.persistence.jpa.implementation.token;

import com.kakaopay.sprinkling_money.domain.token.QToken;
import com.kakaopay.sprinkling_money.domain.token.Token;
import com.kakaopay.sprinkling_money.domain.token.TokenRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryImpl extends QuerydslRepositorySupport implements TokenRepositoryCustom {

    public TokenRepositoryImpl() {
        super(Token.class);
    }

    @Override
    public int saveIfNoExists(Token token) {
        return getEntityManager().createNativeQuery(
                "INSERT INTO token (token_value) " +
                        "SELECT :tokenValue FROM dual " +
                        "WHERE NOT EXISTS (SELECT * FROM token WHERE token_value = :tokenValue);")
                .setParameter("tokenValue", token.getValue())
                .executeUpdate();
    }

    @Override
    public Token findByValue(String tokenValue) {
        QToken qToken = QToken.token;

        return from(qToken)
                .where(qToken.value.eq(tokenValue))
                .fetchFirst();
    }
}
