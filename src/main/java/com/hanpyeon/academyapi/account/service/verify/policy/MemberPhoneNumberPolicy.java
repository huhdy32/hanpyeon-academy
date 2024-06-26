package com.hanpyeon.academyapi.account.service.verify.policy;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.InvalidAccountPolicy;
import com.hanpyeon.academyapi.exception.ErrorCode;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

@Component
class MemberPhoneNumberPolicy implements AccountPolicy {
    private static final String PHONE_NUMBER_STARTS_POLICY = "010";

    @Override
    public void verify(final Member member) {
        final String phoneNumber = member.getPhoneNumber();
        if (!phoneNumber.startsWith(PHONE_NUMBER_STARTS_POLICY)) {
            throw new InvalidAccountPolicy("전화번호는 " + PHONE_NUMBER_STARTS_POLICY + " 로 시작", ErrorCode.ACCOUNT_POLICY);
        }
        if (!phoneNumber.matches("^[0-9]+$")) {
            throw new InvalidAccountPolicy("전화번호는 숫자여야 합니다.", ErrorCode.ACCOUNT_POLICY);
        }
        if (phoneNumber.length() != 11) {
            throw new InvalidAccountPolicy("전화번호는 11자리 숫자여야 합니다", ErrorCode.ACCOUNT_POLICY);
        }
    }
}
