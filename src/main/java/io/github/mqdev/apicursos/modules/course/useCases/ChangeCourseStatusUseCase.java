package io.github.mqdev.apicursos.modules.course.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.CourseRepository;

@Service
public class ChangeCourseStatusUseCase {
    
    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity execute(String id, String status) {
        var course = this.courseRepository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setStatus(status);
        return this.courseRepository.save(course);
    }
}
