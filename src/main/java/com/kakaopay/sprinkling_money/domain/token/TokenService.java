package com.kakaopay.sprinkling_money.domain.token;

import com.kakaopay.sprinkling_money.common.token.TokenConfiguration;
import com.kakaopay.sprinkling_money.domain.token.exception.TokenException;
import com.kakaopay.sprinkling_money.domain.token.exception.code.TokenExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenFactory tokenFactory;

    @Autowired
    private TokenConfiguration tokenConfiguration;

    public Token generateToken() {
        Token token = tokenFactory.issueToken();

        try {
            while (tokenRepository.saveIfNoExists(token) == 0) {
                Thread.sleep(tokenConfiguration.getRelaxTimeMillis());
            }
        } catch (InterruptedException ex) {
            throw new TokenException(TokenExceptionCode.TOKEN_ISSUE_FAILED);
        }

        return tokenRepository.findByValue(token.getValue());
    }

}
