package nl.imine.WebConsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class WebConsoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebConsoleApplication.class, args);
	}
}
