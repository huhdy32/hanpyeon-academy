package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "COURSE")
@NoArgsConstructor
@Getter
class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    @CreationTimestamp
    private LocalDateTime registeredDateTime;
    @ManyToOne
    private Member teacher;
    @OneToMany(mappedBy = "courseEntity")
    private List<CourseStudent> students = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Memo> memos = new ArrayList<>();

    Course(final String courseName, final Member teacher) {
        this.courseName = courseName;
        this.teacher = teacher;
    }

    void changeCourseName(final String newCourseName) {
        this.courseName = newCourseName;
    }

    void changeTeacher(final Member newTeacher) {
        this.teacher = newTeacher;
    }

    void addCourseStudent(final CourseStudent courseStudent) {
        this.students.add(courseStudent);
    }

    public List<CourseStudent> getCourseStudents() {
        return this.students;
    }
}
