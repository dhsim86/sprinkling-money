package com.kakaopay.sprinkling_money.common.distribution;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "distribution")
public class DistributionConfiguration {

    @NotNull
    private Integer validDay;

}
