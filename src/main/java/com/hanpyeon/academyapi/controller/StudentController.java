package com.hanpyeon.academyapi.controller;

import com.hanpyeon.academyapi.dto.StudentRegisterRequestDto;
import com.hanpyeon.academyapi.service.StudentRegisterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/students")
public class StudentController {
    private final Logger LOGGER = LoggerFactory.getLogger("학생 컨트롤러");

    StudentRegisterService registerService;

    public StudentController(StudentRegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegisterRequestDto studentRegisterRequestDto) {
        registerService.registerMember(studentRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
