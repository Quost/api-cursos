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

import io.github.mqdev.apicursos.modules.course.dto.CourseDTO;
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
    private CourseStatusEnum active = CourseStatusEnum.Ativo;

    @NotBlank(message = "Teacher is required")
    private String teacher;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CourseEntity(String name, CourseCategoryEnum category, CourseStatusEnum status, String teacher) {
        this.name = name;
        this.category = category;
        this.active = status;
        this.teacher = teacher;
    }

    public void setActive(String status) {
        this.active = CourseStatusEnum.valueOf(status);
    }

    private void setActive() {
        this.active = this.active == CourseStatusEnum.Ativo ? CourseStatusEnum.Inativo : CourseStatusEnum.Ativo;
    }

    public static CourseEntity fromDTO(CourseDTO courseDTO) {
        CourseCategoryEnum categoryEnum = null;
        CourseStatusEnum statusEnum = null;

        try {
            categoryEnum = CourseCategoryEnum.valueOf(courseDTO.getCategory());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid category");
        }

        try {
            statusEnum = CourseStatusEnum.valueOf(courseDTO.getStatus());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid status");
        }

        return new CourseEntity(courseDTO.getName(), categoryEnum, statusEnum, courseDTO.getTeacher());
    }

    public void update(CourseDTO courseDTO) {
        if (courseDTO.getName() != null) {
            this.name = courseDTO.getName();
        }

        if (courseDTO.getCategory() != null) {
            try {
                this.category = CourseCategoryEnum.valueOf(courseDTO.getCategory());
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Invalid category");
            }
        }
    }

    public void setStatus(String status) {
        if (status != null) {
            try {
                setActive(status);
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new IllegalArgumentException("Invalid status");
            }
        } else {
            setActive();
        }
    }
}
