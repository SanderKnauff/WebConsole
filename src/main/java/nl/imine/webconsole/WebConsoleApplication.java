package nl.imine.webconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WebConsoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebConsoleApplication.class, args);
	}
}
