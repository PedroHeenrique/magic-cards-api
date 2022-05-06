package br.com.magiccards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class MagicCardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicCardsApplication.class, args);
	}

}
