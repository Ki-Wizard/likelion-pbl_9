package com.likelion.pbl.member.controller;

import com.likelion.pbl.member.domain.Member;
import com.likelion.pbl.member.dto.LionCreateRequest;
import com.likelion.pbl.member.dto.LionUpdateRequest;
import com.likelion.pbl.member.dto.MemberResponse;
import com.likelion.pbl.member.dto.StaffCreateRequest;
import com.likelion.pbl.member.dto.StaffUpdateRequest;
import com.likelion.pbl.member.service.MemberService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/lions")
    public ResponseEntity<MemberResponse> createLion(@RequestBody LionCreateRequest request) {
        Member member = memberService.createLion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(member));
    }

    @PostMapping("/members/staffs")
    public ResponseEntity<MemberResponse> createStaff(@RequestBody StaffCreateRequest request) {
        Member member = memberService.createStaff(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(member));
    }

    @GetMapping("/members")
    public List<MemberResponse> findAll() {
        return memberService.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> findById(@PathVariable Long id) {
        Member member = memberService.findById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    @PutMapping("/members/lions/{id}")
    public ResponseEntity<MemberResponse> updateLion(
            @PathVariable Long id,
            @RequestBody LionUpdateRequest request
    ) {
        Member member = memberService.updateLion(id, request);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    @PutMapping("/members/staffs/{id}")
    public ResponseEntity<MemberResponse> updateStaff(
            @PathVariable Long id,
            @RequestBody StaffUpdateRequest request
    ) {
        Member member = memberService.updateStaff(id, request);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (!memberService.deleteMember(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
