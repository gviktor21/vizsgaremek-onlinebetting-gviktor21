package com.gviktor.onlinebet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//Nagy megcsinalni list
//todo: hozzáadni a maradék service-eket és a Controllereket, kivéve a 6-os és SkandinávLottó-t (Csak akkor lesz leimplementálva ha marad rá idő)
//todo:loggingolás és swagger hozzáadása
//todo: flyway hozzáadása
//todo: docker konténer elkészítése
//todo: if-ekben előrerakni a bindingresultTesztelést.
//todo: legyen a bidType EventType vagy elhagyható
//todo: tesztmetódusok nevét checkolni, eléírni test szót nem ártana
//todo: BidLotto5 hozzáadásnál tesztelni hogy létezik e a bid-id
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
