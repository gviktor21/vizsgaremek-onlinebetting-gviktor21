package com.gviktor.onlinebet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//Nagy megcsinalni list
//todo:loggingolás és swagger hozzáadása
//todo: docker konténer elkészítése
//todo: if-ekben előrerakni a bindingresultTesztelést.
//todo: tesztmetódusok nevét checkolni, eléírni test szót nem ártana
//todo: TestData-ba kirakni testadatokat
//todo: Pár mappert átnézni.
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
