package io.github.mqdev.apicursos.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.exceptions.CourseAlreadyExistsException;
import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.CourseRepository;
import io.github.mqdev.apicursos.modules.course.dto.CourseDTO;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity execute(CourseDTO courseDTO) {
        this.courseRepository.findByName(courseDTO.getName()).ifPresent(course -> {
            throw new CourseAlreadyExistsException("The course already exists");
        });

        return this.courseRepository.save(CourseEntity.fromDTO(courseDTO));
    }
    
}
