package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class CourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime registeredDateTime;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course courseEntity;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Member member;

    private CourseStudent(final Member student, final Course courseEntity) {
        this.member = student;
        this.courseEntity = courseEntity;
    }

    static CourseStudent addToCourse(final Member student, final Course courseEntity) {
        final CourseStudent courseStudent = new CourseStudent(student, courseEntity);
        courseEntity.addCourseStudent(courseStudent);
        return new CourseStudent(student, courseEntity);
    }

    public void delete() {
        this.member = null;
        this.courseEntity = null;
    }
}
