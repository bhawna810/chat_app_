package com.example.peer_to_peer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class })
@SpringBootApplication
@EnableDiscoveryClient
public class PeerToPeerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeerToPeerApplication.class, args);
	}

}
