package com.example.search;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//import com.example.search.modal.Event;
import com.example.user_events.UserCreatedEvent;

@Service
public class UserConsumeEvent {

	@KafkaListener(topics= "user-created", groupId = "search" )
	public void consume(UserCreatedEvent event) {
		
		System.out.println("-----------------------------");
		
		System.out.println("event value is " + event.getId());
		System.out.println("event value is " + event.getUsername());
	}
	
}
