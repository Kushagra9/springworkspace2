package com.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.reddit.dto.CommentDTO;
import com.reddit.exception.PostNotFoundException;
import com.reddit.mapper.CommentMapper;
import com.reddit.model.Comment;
import com.reddit.model.NotificationEmail;
import com.reddit.model.Post;
import com.reddit.model.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service 
@AllArgsConstructor
public class CommentService {

	private PostRepository postRepository;
	private AuthService authService;
	private CommentRepository commentRepository;
	private CommentMapper commentMapper;
	private UserRepository userRepository;
	private MailContentBuilder mailContentBuilder;
	private MailService mailService;
	public void save (CommentDTO commentDTO) {
		Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(()->new PostNotFoundException(commentDTO.getId().toString()));
		Comment comment = commentMapper.map(commentDTO, post, authService.getCurrentUser());	
	commentRepository.save(comment);
	String message=mailContentBuilder.build(post.getUser().getUsername()+" posted a comment on your post.");
	sendCommentNotification(message, post.getUser());
	}
	private void sendCommentNotification(String message,User user) {
		// TODO Auto-generated method stub
		mailService.sendEmail(new NotificationEmail(user.getUsername()+" Commented on your post", user.getEmail(), message));
	}
	public List<CommentDTO> getAllCommentsForPost(Long postId) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException(postId.toString()));
	return	commentRepository.findByPost(post)
		.stream()
		.map(commentMapper::mapToDto)
		.collect(Collectors.toList());
		
	}
	public List<CommentDTO> getAllCommentsForUser(@PathVariable String userName) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(userName));
	//s	System.out.println(commentRepository.findAllByUser(user));
	//s	System.out.println(commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList()));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
		
		
	}
	
}
