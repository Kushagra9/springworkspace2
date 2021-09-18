package com.reddit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.exception.PostNotFoundException;
import com.reddit.exception.SpringRedditException;
import com.reddit.exception.SpringRedditNotFoundException;
import com.reddit.mapper.PostMapper;
import com.reddit.model.Post;
import com.reddit.model.Subreddit;
import com.reddit.model.User;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubredditRepository;
import com.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private List<Post> findAllBySubreddit;
	public Post save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName( postRequest.getSubredditName()).orElseThrow(()->new SpringRedditException(postRequest.getDescription()));
		User user=authService.getCurrentUser();
	
		return 	postRepository.save(postMapper.map(postRequest, subreddit, user));
		
	}
	
	@Transactional(readOnly=true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}
	@Transactional(readOnly=true)
	public List<PostResponse> getAllPosts(){
		return postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
	public List<PostResponse> getPostsBySubreddit(Long subredditId){
		Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(()->new SpringRedditNotFoundException(subredditId.toString()));
		  List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		  return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}

	public List<PostResponse> getPostsByUsername(String username){
		User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
		return postRepository.findByUser(user).stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	
}
