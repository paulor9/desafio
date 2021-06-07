package com.pra.desafio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DesafioApplication {
	private static final Logger logger = LoggerFactory.getLogger(DesafioApplication.class);


	public static void main(String[] args) {

		logger.debug("Current Directory = {}.", System.getProperty("user.dir"));
		SpringApplication.run(DesafioApplication.class, args);

	}


}
