package io.github.mqdev.apicursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ApiCursosApplication {

	public static void main(String[] args) {
		if (isRunningLocally()) {
			loadEnvironmentVariables();
		}

		SpringApplication.run(ApiCursosApplication.class, args);
	}

	private static boolean isRunningLocally() {
		try {
			String profile = System.getProperty("spring.profiles.active");
			return profile == null || profile.equals("local");
		} catch (Exception e) {
			return false;
		}
	}

	private static void loadEnvironmentVariables() {
		try {
			System.out.println("Loading environment variables...");
			Dotenv dotenv = Dotenv.configure().load();

			String databaseUrl = dotenv.get("DATABASE_URL");
			String databaseUsername = dotenv.get("DATABASE_USERNAME");
			String databasePassword = dotenv.get("DATABASE_PASSWORD");
			String jwtSecret = dotenv.get("JWT_SECRET");
			String jwtIssuer = dotenv.get("JWT_ISSUER");

			System.setProperty("spring.datasource.url", databaseUrl);
			System.setProperty("spring.datasource.username", databaseUsername);
			System.setProperty("spring.datasource.password", databasePassword);
			System.setProperty("security.jwt.secret", jwtSecret);
			System.setProperty("security.jwt.issuer", jwtIssuer);

			System.out.println("Environment variables loaded successfully!");
		} catch (Exception e) {
			System.out.println("Error loading environment variables: " + e.getMessage());
		}

	}

}
