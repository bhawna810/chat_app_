package com.example.authentication.services;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//import com.example.authentication.modal.Event;
//Auth Service
import com.example.user_events.UserCreatedEvent;


@Service
public class UserEventProducer {

	private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
	
	public UserEventProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendUserEvent(UserCreatedEvent event) {
		
		System.out.println("-----------------------------");
		
		kafkaTemplate.send(
				"user-created",
	            event.getId().toString(),
	            event
         );
	}
	
}
