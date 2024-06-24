package com.hanpyeon.academyapi.board;

import com.hanpyeon.academyapi.account.MemberInitializer;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.security.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardTestDataInitializer {
    private final MemberInitializer memberInitializer;
    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;

    @PostConstruct
    void init() {
        List<Member> students = memberRepository.findMembersByRole(Role.STUDENT);
        List<Member> teachers = memberRepository.findMembersByRole(Role.TEACHER);
        List<Question> questions = new ArrayList<>();
        for (Member student : students) {
            for (Member teacher : teachers) {
                questions.add(Question.builder().content("test").ownerMember(student).targetMember(teacher).title("test").images(List.of()).build());
            }
        }
        questionRepository.saveAll(questions);
    }
}
