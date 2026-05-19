package com.ch4assignment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberRequest {

    private String name;
    private int age;
    private String mbti;
}
