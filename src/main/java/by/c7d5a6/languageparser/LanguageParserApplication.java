package by.c7d5a6.languageparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class LanguageParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageParserApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(5000))
				.setReadTimeout(Duration.ofMillis(5000))
				.build();
	}
}
