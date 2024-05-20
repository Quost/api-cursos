package io.github.mqdev.apicursos.modules.course.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.modules.course.CourseEntity;
import io.github.mqdev.apicursos.modules.course.CourseRepository;
import io.github.mqdev.apicursos.modules.course.dto.CourseResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListAllCoursesUseCase {

    @Autowired
    private CourseRepository courseRepository;


    public List<CourseResponseDTO> execute(String name, String category) {
        List<CourseResponseDTO> courses = new ArrayList<>(); 

        if (name != null){
            List<CourseEntity> coursesFilteredByName = new ArrayList<>();
            this.courseRepository.findByNameContaining(name).ifPresent(coursesFilteredByName::addAll);

            if (category != null){
                coursesFilteredByName.forEach(course -> {
                    if (course.getCategory().name().contains(category)){
                        courses.add(CourseResponseDTO.fromEntity(course));
                    }
                });
            } else {
                coursesFilteredByName.forEach(course -> {
                    courses.add(CourseResponseDTO.fromEntity(course));
                });
            }
        } else if (category != null){
            this.courseRepository.findAll().forEach(course -> {
                if (course.getCategory().name().contains(category)){
                    courses.add(CourseResponseDTO.fromEntity(course));
                }
            });
        } else {
            this.courseRepository.findAll().forEach(course -> {
                courses.add(CourseResponseDTO.fromEntity(course));
            });
        }

        return courses;
    }
}
