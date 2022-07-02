package com.gviktor.onlinebet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Nagy megcsinalni list
//todo: docker konténer elkészítése
//todo: tesztmetódusok nevét checkolni, eléírni test szót nem ártana
//todo: TestData-ba kirakni testadatokat
//todo: readme.md elkészítenni és postmannel az ottani endpointokat tesztelni
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
