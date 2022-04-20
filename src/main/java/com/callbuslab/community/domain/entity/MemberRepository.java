package com.callbuslab.community.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByAccountId(String accountId);
}
