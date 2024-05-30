package io.github.mqdev.apicursos.modules.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import io.github.mqdev.apicursos.modules.course.CourseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {
    
    private UUID id;
    private String name;
    private String category;
    private String status;
    private String teacher;

    public static CourseResponseDTO fromEntity(CourseEntity entity) {
        return CourseResponseDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .category(entity.getCategory().name())
            .status(entity.getActive().name())
            .teacher(entity.getTeacher())
            .build();
    }
}
