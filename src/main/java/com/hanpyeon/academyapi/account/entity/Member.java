package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer grade;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Boolean removed = false;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registeredDate;

    public void setName(final String name) {
        this.name = name;
    }
    public void setGrade(final Integer grade) {
        this.grade = grade;
    }

    public void setRemoved(final Boolean removeStatus) {
        this.removed = removeStatus;
    }
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(final String password) {
        this.password = password;
    }

    @Builder
    private Member(String phoneNumber, String name, String encryptedPassword, Integer grade, Role role, LocalDateTime registeredDate) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = encryptedPassword;
        this.grade = grade;
        this.role = role;
        this.registeredDate = registeredDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", memberName='" + name + '\'' +
                ", password='" + "****" + '\'' +
                ", grade=" + grade +
                ", userRole=" + role +
                ", localDateTime=" + registeredDate +
                '}';
    }
}
