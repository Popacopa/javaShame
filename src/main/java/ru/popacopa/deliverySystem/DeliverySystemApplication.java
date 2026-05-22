package ru.popacopa.deliverySystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.repository.ClientRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.popacopa.deliverySystem.repository")
@EntityScan(basePackages = "ru.popacopa.deliverySystem.entity")
public class DeliverySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliverySystemApplication.class, args);
	}

}
