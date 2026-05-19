package com.ch4assignment.dto;

import com.ch4assignment.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMemberResponse {

    private Long id;
    private String name;
    private int age;
    private String mbti;

    public static CreateMemberResponse from(Member member) {
        return new CreateMemberResponse(
                member.getId(),
                member.getName(),
                member.getAge(),
                member.getMbti()
        );
    }
}
