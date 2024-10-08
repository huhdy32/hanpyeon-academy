package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.JwtDto;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.InvalidPasswordException;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    Member member;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    PasswordHandler passwordHandler;
    JwtService loginService;

    @BeforeEach
    void initLoginService() {
        this.loginService = new JwtService(memberRepository, jwtUtils, passwordHandler);
    }

    @Test
    void 사용자_찾기_실패_테스트() {
        Mockito.when(memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(Mockito.any()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            loginService.provideJwtByLogin("hi", "hello");
        }).isInstanceOf(NoSuchMemberException.class);
    }

    @Test
    void 비밀번호_에러처리() {
        member = Member.builder()
                .encryptedPassword("010001010")
                .encryptedPassword("12345")
                .build();

        Mockito.when(memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(Mockito.anyString()))
                .thenReturn(Optional.of(member));
        Mockito.when(passwordHandler.matches(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(false);

        assertThatThrownBy(() -> loginService.provideJwtByLogin("1212", "12"))
                .isInstanceOf(InvalidPasswordException.class);

    }
    @Test
    void 비밀번호_성공처리() {
        member = Member.builder()
                .name("1")
                .encryptedPassword("010001010")
                .role(Role.STUDENT)
                .build();

        Mockito.when(memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(Mockito.anyString()))
                .thenReturn(Optional.of(member));
        Mockito.when(passwordHandler.matches(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(true);
        Mockito.when(jwtUtils.generateAccessToken(member.getId(), member.getRole(), member.getName()))
                        .thenReturn("result");
        Mockito.when(jwtUtils.generateRefreshToken(member.getId()))
                        .thenReturn("result");

        assertThat(loginService.provideJwtByLogin("1212", "12"))
                .isEqualTo(new JwtDto("1", "result", "result", Role.STUDENT));
    }
}