package com.likelion.pbl.member.dto;

public record StaffCreateRequest(
        String name,
        String major,
        String part,
        int generation,
        String position
) {
}
