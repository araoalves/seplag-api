package br.gov.mt.seplag.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "${info.build.name}", version = "${info.build.version}", description = "${info.app.description}",
		contact = @io.swagger.v3.oas.annotations.info.Contact(name = "Time SEPLAG", email = "contato@seplag.gov.br")))
@ComponentScan("br.gov")
@SpringBootApplication
public class SeplagApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeplagApiApplication.class, args);
	}

}
