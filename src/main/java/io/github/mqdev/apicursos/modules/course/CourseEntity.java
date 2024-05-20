package io.github.mqdev.apicursos.modules.course;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.github.mqdev.apicursos.modules.course.enums.CourseCategoryEnum;
import io.github.mqdev.apicursos.modules.course.enums.CourseStatusEnum;
import lombok.Data;

@Data
@Entity(name = "course")
public class CourseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name is required")    
    private String name;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    private CourseCategoryEnum category;

    @Enumerated(EnumType.STRING)
    private CourseStatusEnum active = CourseStatusEnum.ACTIVE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
