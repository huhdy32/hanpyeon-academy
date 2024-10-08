package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountGrade {
    private static final int GRADE_MIN = 0;
    private static final int GRADE_MAX = 11;
    private final Integer grade;

    void validate(final AccountRole accountRole) {
        if (accountRole.getRole().equals(Role.STUDENT)) {
            if (Objects.isNull(this.grade)) {
                throw new AccountException("학생은 grade가 Null일 수 없습니다.", ErrorCode.ACCOUNT_POLICY);
            }
        }
    }


    private static void validate(final Integer grade) {
        if (Objects.isNull(grade)) {
            return;
        }
        if (grade < GRADE_MIN || grade > GRADE_MAX) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
    }

    static AccountGrade of(final Integer grade) {
        validate(grade);
        return new AccountGrade(grade);
    }
}
