package io.github.mqdev.apicursos.modules.course.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.CourseRepository;
import io.github.mqdev.apicursos.modules.course.dto.CourseDTO;

@Service
public class UpdateCourseUseCase {
    
    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity execute(String id, CourseDTO courseDTO) {
        var course = this.courseRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Course not found"));
        course.update(courseDTO);
        return this.courseRepository.save(course);
    }
}
