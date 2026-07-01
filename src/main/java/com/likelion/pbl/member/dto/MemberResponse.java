package com.likelion.pbl.member.dto;

import com.likelion.pbl.member.domain.Member;

public record MemberResponse(
        Long id,
        String name,
        String major,
        String part,
        int generation,
        String roleName,
        String studentId,
        String position
) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getMajor(),
                member.getPart(),
                member.getGeneration(),
                member.getRoleType().getDisplayName(),
                member.getStudentId(),
                member.getPosition()
        );
    }
}
