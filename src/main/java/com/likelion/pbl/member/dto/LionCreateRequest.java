package com.likelion.pbl.member.dto;

public record LionCreateRequest(
        String name,
        String major,
        String part,
        int generation,
        String studentId
) {
}
