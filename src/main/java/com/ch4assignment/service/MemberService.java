package com.ch4assignment.service;

import com.ch4assignment.dto.CreateMemberRequest;
import com.ch4assignment.dto.CreateMemberResponse;
import com.ch4assignment.dto.GetMemberResponse;
import com.ch4assignment.entity.Member;
import com.ch4assignment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public CreateMemberResponse saveMember(CreateMemberRequest request) {

        Member member = new Member(
                request.getName(),
                request.getAge(),
                request.getMbti()
        );

        Member savedMember = memberRepository.save(member);
        return CreateMemberResponse.from(savedMember);
    }

    @Transactional(readOnly = true)
    public GetMemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 팀원입니다.")
        );
        return GetMemberResponse.from(member);
    }

    @Transactional
    public void updateMemberImageKey(Long id, String key) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 팀원입니다.")
        );
        member.uploadProfileImageUrl(key);
    }

    @Transactional(readOnly = true)
    public String getMemberImageKey(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 팀원입니다.")
        );
        if (member.getProfileImageUrl() == null || member.getProfileImageUrl().isEmpty()) {
            throw new IllegalArgumentException("등록된 이미지가 없습니다.");
        }
        return member.getProfileImageUrl();

    }
}
