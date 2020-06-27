package com.kakaopay.sprinkling_money.domain.distribution;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionRepository extends JpaRepository<Distribution, String>, DistributionRepositoryCustom {

}
