package com.reddit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reddit.dto.CommentDTO;
import com.reddit.dto.VoteDTO;
import com.reddit.mapper.CommentMapper;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;
import com.reddit.service.CommentService;
import com.reddit.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {

	public VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDto) {
	voteService.vote(voteDto);
	return new ResponseEntity<>(HttpStatus.CREATED);
	}
		
	
}
