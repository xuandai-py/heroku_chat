package com.heroku.chat.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.heroku.chat.model.Message;

@Service
public interface MessageService {
	List<Message> findAll();
	Optional<Message> findLatesMessage(String username);
	void save(Message message);
	void remove(Message message);
	Optional<Message> findById(Long id);
	List<Message> findByUsername(String username);
}
