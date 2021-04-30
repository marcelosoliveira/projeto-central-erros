package com.projeto.centralerros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class CentralerrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralerrosApplication.class, args);

		@Configuration
		@EnableWebMvc
		public static class WebConfig extends WebMvcConfigurerAdapter {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		}
	}

}
