package traveler_service.traveler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "traveler_service.traveler_service.feignclients")

public class TravelerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelerServiceApplication.class, args);
	}

}
