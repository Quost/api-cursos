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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "course")
@AllArgsConstructor
@NoArgsConstructor
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

        statusEnum = CourseStatusEnum.INACTIVE;

        return new CourseEntity(courseRequestDTO.name(), categoryEnum, statusEnum);
    }

    public void update(CourseRequestDTO courseRequestDTO) {
        if (courseRequestDTO.name() != null) {
            this.name = courseRequestDTO.name();
        }

        if (courseRequestDTO.category() != null) {
            try {
                this.category = CourseCategoryEnum.valueOf(courseRequestDTO.category());
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Invalid category");
            }
        }
    }

    public void setStatus(String status) {
        if (status != null) {
            try {
                this.active = CourseStatusEnum.valueOf(status);
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Invalid status");
            }
        } else {
            this.active = this.active == CourseStatusEnum.ACTIVE ? CourseStatusEnum.INACTIVE : CourseStatusEnum.ACTIVE;
        }
    }
}
