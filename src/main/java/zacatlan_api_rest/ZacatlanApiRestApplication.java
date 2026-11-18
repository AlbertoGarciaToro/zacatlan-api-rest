package zacatlan_api_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "mx.unam.fes.zacatlan")
public class ZacatlanApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZacatlanApiRestApplication.class, args);
	}

}
