package com.kakaopay.sprinkling_money.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

}
