package com.kakaopay.sprinkling_money.domain;

import com.kakaopay.sprinkling_money.common.secure.SecureRandomGenerator;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Setter
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = {"amount"})
public class Money {

    @Column(name = "money")
    private Long amount = 0L;

    public List<Money> distribute(SecureRandomGenerator generator, int count) {
        if (amount < count) {
            throw new IllegalArgumentException("amount is lesser than count");
        }
        long quarter = amount / count;
        long surplus = amount % count;

        int randomIndex = generator.generateRandomNumber(count);

        List<Money> moneyList = LongStream.range(0, count)
                .map(i -> i == randomIndex ? (amount / count + amount % count) : (amount / count))
                .mapToObj(Money::new)
                .collect(Collectors.toList());
        return moneyList;
    }

}
