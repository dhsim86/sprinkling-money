package com.kakaopay.sprinkling_money.domain;

import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTest {

    private SecureRandomGenerator generator;

    @BeforeEach
    public void init() {
        generator = new SecureRandomGenerator();
    }

    @Test
    public void distributeTest() {
        Money money;
        int count;

        List<Money> resultList;

        given:
        {
            money = new Money(1000L);
            count = 3;
        }

        when:
        {
            resultList = money.distribute(generator, count);
        }

        then:
        {
            assertThat(resultList).isNotEmpty()
                    .hasSize(3)
                    .containsExactlyInAnyOrder(new Money(333L), new Money(333L), new Money(334L));
        }
    }

    @Test
    public void distributeFailureTest() {
        Money money;
        int count;

        given:
        {
            money = new Money(10L);
            count = 20;
        }

        then:
        {
            assertThrows(IllegalArgumentException.class, () -> money.distribute(generator, count));
        }
    }

}
