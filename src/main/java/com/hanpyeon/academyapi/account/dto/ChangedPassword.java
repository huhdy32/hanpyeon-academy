package com.hanpyeon.academyapi.account.dto;

public record ChangedPassword(
        String phoneNumber,
        String changedPassword
) {
}
