package com.leesungjae.tourist_hub_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TouristHubBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TouristHubBatchApplication.class, args);
	}

}
