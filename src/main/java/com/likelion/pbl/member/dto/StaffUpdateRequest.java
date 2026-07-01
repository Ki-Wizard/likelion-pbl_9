package com.likelion.pbl.member.dto;

public record StaffUpdateRequest(
        String name,
        String major,
        String part,
        String position
) {
}
