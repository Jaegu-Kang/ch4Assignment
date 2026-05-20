package com.ch4assignment.controller;

import com.ch4assignment.dto.*;
import com.ch4assignment.service.MemberService;
import com.ch4assignment.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final S3Service s3Service;

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

    @PostMapping("/members/{id}/profile-image")
    public ResponseEntity<FileUploadResponse> upload(
            @PathVariable Long id,
            @RequestParam("file")MultipartFile file) {

        String key = s3Service.upload(file);

        memberService.updateMemberImageKey(id, key);

        return ResponseEntity.ok(new FileUploadResponse(key));
    }

    @GetMapping("/members/{id}/profile-image")
    public ResponseEntity<FileDownloadUrlResponse> getDownloadUrl(
            @PathVariable Long id) {

        String key = memberService.getMemberImageKey(id);

        URL url = s3Service.getDownloadUrl(key);

        return ResponseEntity.ok(new FileDownloadUrlResponse(url.toString()));
    }

}
