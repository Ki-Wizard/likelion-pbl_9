package com.likelion.pbl.member.service;

import com.likelion.pbl.member.domain.Member;
import com.likelion.pbl.member.domain.RoleType;
import com.likelion.pbl.member.dto.LionCreateRequest;
import com.likelion.pbl.member.dto.LionUpdateRequest;
import com.likelion.pbl.member.dto.StaffCreateRequest;
import com.likelion.pbl.member.dto.StaffUpdateRequest;
import com.likelion.pbl.member.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member createLion(LionCreateRequest request) {
        Member member = new Member(
                request.name(),
                request.major(),
                request.part(),
                request.generation(),
                RoleType.LION,
                request.studentId(),
                null
        );
        return memberRepository.save(member);
    }

    @Transactional
    public Member createStaff(StaffCreateRequest request) {
        Member member = new Member(
                request.name(),
                request.major(),
                request.part(),
                request.generation(),
                RoleType.STAFF,
                null,
                request.position()
        );
        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Transactional
    public Member updateLion(Long id, LionUpdateRequest request) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null || member.getRoleType() != RoleType.LION) {
            return null;
        }
        member.updateInfo(request.name(), request.major(), request.part());
        member.updateStudentId(request.studentId());
        return member;
    }

    @Transactional
    public Member updateStaff(Long id, StaffUpdateRequest request) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null || member.getRoleType() != RoleType.STAFF) {
            return null;
        }
        member.updateInfo(request.name(), request.major(), request.part());
        member.updatePosition(request.position());
        return member;
    }

    @Transactional
    public boolean deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            return false;
        }
        memberRepository.deleteById(id);
        return true;
    }
}
