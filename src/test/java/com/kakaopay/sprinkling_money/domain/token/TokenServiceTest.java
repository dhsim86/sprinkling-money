package com.kakaopay.sprinkling_money.domain.token;

import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import com.kakaopay.sprinkling_money.common.secure.SecureRandomGeneratorTest;
import com.kakaopay.sprinkling_money.common.token.TokenConfiguration;
import com.kakaopay.sprinkling_money.domain.token.exception.TokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TokenServiceTest {

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private TokenFactory tokenFactory;

    @MockBean
    private TokenConfiguration tokenConfiguration;

    @Autowired
    private TokenService tokenService;

    private SecureRandomGenerator secureRandomGenerator;

    @BeforeEach
    public void init() {
        secureRandomGenerator = new SecureRandomGenerator();
    }

    @Test
    public void generateTokenTest() {
        Token token;
        Token resultToken;
        String tokenValue;

        given:
        {
            tokenValue = secureRandomGenerator.generateRandomString(tokenConfiguration.getMaxLength());
            token = new Token(tokenValue);

            doReturn(token).when(tokenFactory).issueToken();
            doReturn(1).when(tokenRepository).saveIfNoExists(any(Token.class));
            doReturn(token).when(tokenRepository).findByValue(anyString());
        }

        when:
        {
            resultToken = tokenService.generateToken();
        }

        then:
        {
            assertThat(resultToken).isNotNull().extracting("value").isEqualTo(tokenValue);
        }
    }

    @Test
    public void generateTokenFailureTest() {
        Token token;
        String tokenValue;

        given:
        {
            tokenValue = secureRandomGenerator.generateRandomString(tokenConfiguration.getMaxLength());
            token = new Token(tokenValue);

            doReturn(token).when(tokenFactory).issueToken();
            doThrow(mock(RuntimeException.class)).when(tokenRepository).saveIfNoExists(any(Token.class));
        }

        then:
        {
            assertThrows(TokenException.class, () -> tokenService.generateToken());
        }
    }

}
