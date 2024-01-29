package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberDto;

public interface MemberVerification {
    boolean supports(RegisterMemberDto memberDto);
    void checkFields(RegisterMemberDto memberDto);
}