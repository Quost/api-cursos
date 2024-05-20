package io.github.mqdev.apicursos.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.exceptions.CourseAlreadyExistsException;
import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.CourseRepository;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity execute(CourseEntity courseEntity) {
        this.courseRepository.findByName(courseEntity.getName()).ifPresent(course -> {
            throw new CourseAlreadyExistsException("The course already exists");
        });

        return this.courseRepository.save(courseEntity);
    }
    
}
