package com.likelion.pbl.member.dto;

public record LionUpdateRequest(
        String name,
        String major,
        String part,
        String studentId
) {
}
