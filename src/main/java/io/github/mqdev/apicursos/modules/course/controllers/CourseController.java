package io.github.mqdev.apicursos.modules.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mqdev.apicursos.modules.course.dto.CourseRequestDTO;
import io.github.mqdev.apicursos.modules.course.useCases.ChangeCourseStatusUseCase;
import io.github.mqdev.apicursos.modules.course.useCases.CreateCourseUseCase;
import io.github.mqdev.apicursos.modules.course.useCases.DeleteCourseUseCase;
import io.github.mqdev.apicursos.modules.course.useCases.ListAllCoursesUseCase;
import io.github.mqdev.apicursos.modules.course.useCases.UpdateCourseUseCase;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/cursos")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @Autowired
    private ListAllCoursesUseCase listAllCoursesUseCase;

    @Autowired
    private UpdateCourseUseCase updateCourseUseCase;

    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;

    @Autowired
    private ChangeCourseStatusUseCase changeCourseStatusUseCase;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createCourse(@Valid @RequestBody CourseRequestDTO course) {
        try {
            var result = this.createCourseUseCase.execute(course);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> listCourses(@RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        try {
            var result = this.listAllCoursesUseCase.execute(name, category);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateCourse(@PathVariable("id") String id,
            @Valid @RequestBody CourseRequestDTO course) {
        try {
            var result = this.updateCourseUseCase.execute(id, course);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteCourse(@PathVariable("id") String id) {
        try {
            this.deleteCourseUseCase.execute(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> changeCourseStatus(@PathVariable("id") String id, @RequestParam(required = false) String active) {
        try {
            var result = this.changeCourseStatusUseCase.execute(id, active);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
