package io.github.mqdev.apicursos.modules.course.useCases;

import org.springframework.stereotype.Service;

import io.github.mqdev.apicursos.modules.course.enums.CourseCategoryEnum;

import java.util.List;
import java.util.ArrayList;

@Service
public class GetCourseCategoriesUseCase {

    public List<String> execute() {
        List<String> categories = new ArrayList<>();
        for (CourseCategoryEnum category : CourseCategoryEnum.values()) {
            categories.add(category.name());
        }
        return categories;
    }
}
