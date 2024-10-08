package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record QuestionUpdateDto(
        @NotNull
        Long questionId,
        @NotNull
        Long targetMemberId,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role memberRole,
        String content,
        String title,
        List<MultipartFile> images
) {
    @Override
    public String toString() {
        return "QuestionUpdateDto{" +
                ", targetMemberId=" + targetMemberId +
                ", requestMemberId=" + requestMemberId +
                ", images=" + images +
                '}';
    }
}
