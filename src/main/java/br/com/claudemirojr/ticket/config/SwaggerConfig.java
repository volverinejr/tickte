package br.com.claudemirojr.ticket.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.claudemirojr.ticket.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());

	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Restfull API com Spring Boot 2.4.1", "API Ticket", "1.0", "Termos de Serviço url",
				new Contact("Claudemiro Jr", "https://www.linkedin.com/in/claudemirojr/", "volverinejr@gmail.com"),
				"Licença", "Licença url", Collections.emptyList());
	}

}
