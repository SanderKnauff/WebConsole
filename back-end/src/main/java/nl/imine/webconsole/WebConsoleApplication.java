package nl.imine.webconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories("nl.imine.webconsole.repository")
public class WebConsoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebConsoleApplication.class, args);
	}

}