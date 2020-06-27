package com.kakaopay.sprinkling_money.domain.distribution;

import com.kakaopay.sprinkling_money.domain.Money;
import com.kakaopay.sprinkling_money.domain.user.User;
import com.kakaopay.sprinkling_money.infrastructure.persistence.jpa.config.converter.YesNoTypeConverter;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class DistributionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "distribution_info_id")
    private Integer id;

    private Integer userId;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "money"))
    private Money money;

    @Convert(converter = YesNoTypeConverter.class)
    private Boolean isReceived;

    @Setter(AccessLevel.PACKAGE)
    @ManyToOne
    @JoinColumn(name = "distribution_id")
    private Distribution distribution;

    public DistributionInfo(Money money) {
        this.money = money;
        this.isReceived = false;
    }

    public boolean isReceived(User user) {
        return isReceived == true && userId.equals(user.getId());
    }

    public boolean isReceived() {
        return isReceived == true;
    }

    public Money allocate(User user) {
        userId = user.getId();
        isReceived = true;
        return money;
    }

}
