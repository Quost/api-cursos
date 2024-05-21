package io.github.mqdev.apicursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "API de Cursos",
		version = "1.0",
		description = "API para cadastro de cursos"
	)
)
public class ApiCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCursosApplication.class, args);
	}

}
