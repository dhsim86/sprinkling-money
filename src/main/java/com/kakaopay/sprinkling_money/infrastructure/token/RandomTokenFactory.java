package com.kakaopay.sprinkling_money.infrastructure.token;

import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import com.kakaopay.sprinkling_money.domain.token.Token;
import com.kakaopay.sprinkling_money.domain.token.TokenFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RandomTokenFactory implements TokenFactory {

    private SecureRandomGenerator secureRandomGenerator;
    private int maxTokenLength;

    @Override
    public Token issueToken() {
        return new Token(secureRandomGenerator.generateRandomString(maxTokenLength));
    }

}
