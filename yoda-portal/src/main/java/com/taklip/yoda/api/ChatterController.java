package com.taklip.yoda.api;

import com.taklip.yoda.model.ChatResponse;
import com.taklip.yoda.service.ChatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatterController {
	@Autowired
	ChatterService chatterService;

	@GetMapping
	public ResponseEntity<ChatResponse> getAnswer(@RequestParam("question") String question) {
		// TODO save questions
		ChatResponse chatResponse = chatterService.getAnswer(question);
		if (chatResponse != null) {
			return new ResponseEntity(chatResponse, HttpStatus.OK);
		}

		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/train")
	public ResponseEntity<String> train() {


		return new ResponseEntity("", HttpStatus.OK);
	}
}
