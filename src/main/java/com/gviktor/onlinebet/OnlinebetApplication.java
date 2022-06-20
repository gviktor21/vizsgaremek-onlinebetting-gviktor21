package com.gviktor.onlinebet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//Nagy megcsinalni list
//todo: Event CRUD-OT tesztelni POstmannel
//todo: Validation-t megcsinálni
//todo:loggingolás és swagger hozzáadása
//todo: flyway hozzáadása
//todo: docker konténer elkészítése
//todo: if-ekben előrerakni a bindingresultTestelést.

@SpringBootApplication
public class OnlinebetApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinebetApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
