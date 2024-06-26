package com.blaxsior.board.domain.member.repository;

import com.blaxsior.board.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String username);
    boolean existsByEmail(String email);
}
