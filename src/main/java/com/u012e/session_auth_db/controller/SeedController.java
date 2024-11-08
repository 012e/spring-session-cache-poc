package com.u012e.session_auth_db.controller;

import com.u012e.session_auth_db.model.Student;
import com.u012e.session_auth_db.service.seeder.Seeder;
import com.u012e.session_auth_db.utils.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("seed")
@RequiredArgsConstructor
public class SeedController {

    private final Seeder<Student> studentSeeder;

    @GetMapping("students")
    public GenericResponse<String> seedStudents(@RequestParam(defaultValue = "100") int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }
        studentSeeder.seed(count);
        return GenericResponse.<String>builder()
                .message("Students seeded")
                .data(null)
                .success(true)
                .build();
    }
}
