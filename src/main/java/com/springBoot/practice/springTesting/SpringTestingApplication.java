package com.springBoot.practice.springTesting;

import com.springBoot.practice.springTesting.service.DataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTestingApplication  implements CommandLineRunner {

	@Value("${my.variable}")
	private String myVariable;
	private final DataService dataService;

    public SpringTestingApplication(DataService dataService) {
        this.dataService = dataService;
    }

    public static void main(String[] args) {
		SpringApplication.run(SpringTestingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("profile "+ myVariable);
		System.out.println(dataService.getData());
	}
}
