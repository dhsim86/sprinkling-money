package com.kakaopay.sprinkling_money.common.secure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecureConfiguration {

    @Bean
    public SecureRandomGenerator secureRandomGenerator() {
        return new SecureRandomGenerator();
    }

}
