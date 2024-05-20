package io.github.mqdev.apicursos.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.exceptions.CourseAlreadyExistsException;
import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.CourseRepository;
import io.github.mqdev.apicursos.modules.course.dto.CourseRequestDTO;

@Service
public class CreateCourseUseCase {

    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity execute(CourseRequestDTO courseRequestDTO) {
        this.courseRepository.findByName(courseRequestDTO.name()).ifPresent(course -> {
            throw new CourseAlreadyExistsException("The course already exists");
        });

        return this.courseRepository.save(CourseEntity.fromDTO(courseRequestDTO));
    }
    
}
