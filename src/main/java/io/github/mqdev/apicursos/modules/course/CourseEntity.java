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

import io.github.mqdev.apicursos.modules.course.dto.CourseRequestDTO;
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

    public CourseEntity(String name, CourseCategoryEnum category, CourseStatusEnum status) {
        this.name = name;
        this.category = category;
        this.active = status;
    }

    public static CourseEntity fromDTO(CourseRequestDTO courseRequestDTO) {
        CourseCategoryEnum categoryEnum = null;
        CourseStatusEnum statusEnum = null;

        try {
            categoryEnum = CourseCategoryEnum.valueOf(courseRequestDTO.category());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid category");
        }

        try {
            statusEnum = CourseStatusEnum.valueOf(courseRequestDTO.status());
        } catch (IllegalArgumentException | NullPointerException e) {
            statusEnum = CourseStatusEnum.ACTIVE;
        }

        return new CourseEntity(courseRequestDTO.name(), categoryEnum, statusEnum);
    }
}
