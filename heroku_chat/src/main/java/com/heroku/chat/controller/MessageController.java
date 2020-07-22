package com.heroku.chat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.heroku.chat.model.Message;
import com.heroku.chat.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;

	@GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Message>> finAllProduct(){
		List<Message> messages = messageService.findAll();
		if (messages.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
	
	@PostMapping(value = "/message/chat")
	public ResponseEntity<Message> createMessage(@RequestBody Message message, UriComponentsBuilder builder){
		messageService.save(message);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/message/{id}").buildAndExpand(message.getId()).toUri());
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/message/{id}")
	public ResponseEntity<Message> deleteProduct(@PathVariable("id") Long id){
		Optional<Message> message = messageService.findById(id);
		if (!message.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		messageService.remove(message.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@GetMapping(value = "/message/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getMessageById(@PathVariable("id") Long id){
		Optional<Message> message = messageService.findById(id);
		
		if(!message.isPresent()) {
			return new ResponseEntity<>(message.get(), HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(message.get(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Message>> getMessageByUsername(@RequestParam(value = "username") String username){
		List<Message> messages = messageService.findByUsername(username);
		if (messages.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(messages, HttpStatus.OK);
	}
}
