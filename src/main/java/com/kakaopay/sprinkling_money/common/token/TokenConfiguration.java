package com.kakaopay.sprinkling_money.common.token;

import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import com.kakaopay.sprinkling_money.domain.token.TokenFactory;
import com.kakaopay.sprinkling_money.infrastructure.token.RandomTokenFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfiguration {

    @NotNull
    private Integer maxLength;

    @NotNull
    private Integer relaxTimeMillis;

    @Autowired
    private SecureRandomGenerator secureRandomGenerator;

    @Bean
    public TokenFactory tokenFactory() {
        return new RandomTokenFactory(secureRandomGenerator, maxLength);
    }

}
