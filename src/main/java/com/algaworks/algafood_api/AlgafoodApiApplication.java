package com.algaworks.algafood_api;

import com.algaworks.algafood_api.infrastructure.repository.CustomJPARepositoryImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJPARepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
				// .directory("./backend") // opcional
				.load();

		System.setProperty("API_EMAIL_KEY", dotenv.get("API_EMAIL_KEY"));

		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
