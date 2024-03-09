package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.exception.IllegalCourseNameException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentStateException;
import com.hanpyeon.academyapi.course.application.exception.NotFoundTeacherException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseTest {

    @Test
    void Course_생성_성공_테스트() {
        final String name = "1212";
        final List<Student> student = List.of(new Student(1l));
        final Teacher teacher = Mockito.mock(Teacher.class);

        assertDoesNotThrow(() -> Course.of(name, student, teacher));
    }

    @Test
    void Course_제목_글자수_초과() {
        final String name = "1".repeat(101);
        final List<Student> students = List.of(new Student(1l));
        final Teacher teacher = new Teacher(2l);

        assertThatThrownBy(() -> Course.of(name, students, teacher))
                .isInstanceOf(IllegalCourseNameException.class);
    }
    @Test
    void Course_학생_상태_이상() {
        final String name = "1".repeat(10);
        final List<Student> students = new ArrayList<>();
        students.add(new Student(1l));
        students.add(null);
        final Teacher teacher = new Teacher(2l);

        assertThatThrownBy(() -> Course.of(name, students, teacher))
                .isInstanceOf(IllegalCourseStudentStateException.class);
    }
    @Test
    void Course_선생_없음() {
        final String name = "1".repeat(10);
        final List<Student> students = List.of(new Student(1l));
        final Teacher teacher = null;

        assertThatThrownBy(() -> Course.of(name, students, teacher))
                .isInstanceOf(NotFoundTeacherException.class);
    }

}