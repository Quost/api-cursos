package io.github.mqdev.apicursos.modules.course;

import java.util.UUID;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByName(String name);
    Optional<List<CourseEntity>> findByNameContaining(String name);
}
