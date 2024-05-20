package io.github.mqdev.apicursos.modules.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.useCases.CreateCourseUseCase;
import io.github.mqdev.apicursos.modules.course.useCases.ListAllCoursesUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @Autowired
    private ListAllCoursesUseCase listAllCoursesUseCase;
    
    @PostMapping
    public ResponseEntity<Object> createCourse(@Valid @RequestBody CourseEntity course) {
        try {
            var result = this.createCourseUseCase.execute(course);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // This method will list all courses
    // It`s optional to filter by name or category
    @GetMapping
    public ResponseEntity<Object> listCourses(@RequestParam(required = false) String name, @RequestParam(required = false) String category) {
        try {
            var result = this.listAllCoursesUseCase.execute(name, category);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
