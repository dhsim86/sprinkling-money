package com.kakaopay.sprinkling_money.common.secure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SecureRandomGeneratorTest {

    private SecureRandomGenerator secureRandomGenerator;

    @BeforeEach
    public void init() {
        secureRandomGenerator = new SecureRandomGenerator();
    }

    @Test
    public void generateRandomNumberTest() {
        int result = 0;

        when:
        {
            result = secureRandomGenerator.generateRandomNumber(10);
        }

        then:
        {
            assertThat(result).isBetween(0, 10);
        }
    }

    @Test
    public void generateRandomStringTest() {
        String result1;
        String result2;

        when:
        {
            result1 = secureRandomGenerator.generateRandomString(3);
            result2= secureRandomGenerator.generateRandomString(3);
        }

        then:
        {
            assertThat(result1).isNotEmpty().hasSize(3);
            assertThat(result2).isNotEmpty().hasSize(3);
            assertThat(result1).isNotEqualTo(result2);
        }
    }

}
