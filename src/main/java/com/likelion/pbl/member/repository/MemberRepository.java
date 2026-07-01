package com.likelion.pbl.member.repository;

import com.likelion.pbl.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
}
