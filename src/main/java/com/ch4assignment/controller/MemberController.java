package com.ch4assignment.controller;

import com.ch4assignment.dto.CreateMemberRequest;
import com.ch4assignment.dto.CreateMemberResponse;
import com.ch4assignment.dto.GetMemberResponse;
import com.ch4assignment.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<CreateMemberResponse> createMember(
            @RequestBody CreateMemberRequest request) {
        return ResponseEntity.ok(memberService.saveMember(request));
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<GetMemberResponse> getMember(
            @PathVariable Long id) {
        if (id == 999) {
            throw new RuntimeException("테스트를 위한 강제 서버 에러 발생!");
        }

        return ResponseEntity.ok(memberService.getMember(id));
    }
}
