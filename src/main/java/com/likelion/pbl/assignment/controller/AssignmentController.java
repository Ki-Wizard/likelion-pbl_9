package com.likelion.pbl.assignment.controller;

import com.likelion.pbl.assignment.domain.Assignment;
import com.likelion.pbl.assignment.dto.AssignmentCreateRequest;
import com.likelion.pbl.assignment.dto.AssignmentResponse;
import com.likelion.pbl.assignment.dto.AssignmentUpdateRequest;
import com.likelion.pbl.assignment.service.AssignmentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/members/{memberId}/assignments")
    public ResponseEntity<AssignmentResponse> create(
            @PathVariable Long memberId,
            @RequestBody AssignmentCreateRequest request
    ) {
        Assignment assignment = assignmentService.create(memberId, request);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(AssignmentResponse.from(assignment));
    }

    @GetMapping("/members/{memberId}/assignments")
    public List<AssignmentResponse> findByMemberId(@PathVariable Long memberId) {
        return assignmentService.findByMemberId(memberId).stream()
                .map(AssignmentResponse::from)
                .toList();
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentResponse> findById(@PathVariable Long id) {
        Assignment assignment = assignmentService.findById(id);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AssignmentResponse.from(assignment));
    }

    @GetMapping("/assignments/search")
    public List<AssignmentResponse> findByTitle(@RequestParam String title) {
        return assignmentService.findByTitleContaining(title).stream()
                .map(AssignmentResponse::from)
                .toList();
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentResponse> update(
            @PathVariable Long id,
            @RequestBody AssignmentUpdateRequest request
    ) {
        Assignment assignment = assignmentService.update(id, request);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AssignmentResponse.from(assignment));
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!assignmentService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
