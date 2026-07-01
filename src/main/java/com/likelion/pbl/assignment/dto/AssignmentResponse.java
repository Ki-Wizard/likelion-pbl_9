package com.likelion.pbl.assignment.dto;

import com.likelion.pbl.assignment.domain.Assignment;

public record AssignmentResponse(
        Long id,
        String title,
        String description,
        Long memberId,
        String memberName
) {

    public static AssignmentResponse from(Assignment assignment) {
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getMember().getId(),
                assignment.getMember().getName()
        );
    }
}
