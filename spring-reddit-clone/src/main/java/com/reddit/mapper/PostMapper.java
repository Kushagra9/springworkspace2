package com.reddit.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.model.Post;
import com.reddit.model.Subreddit;
import com.reddit.model.User;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.VoteRepository;
import com.reddit.service.AuthService;

@Mapper(componentModel="spring")
public abstract class PostMapper {
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private AuthService authService;
	
	 @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
	@Mapping(target="subreddit", source="subreddit")
	@Mapping(target="user",source="user")
	@Mapping(target="description",source="postRequest.description")
	@Mapping(target="voteCount",constant="0")
	public abstract Post map(PostRequest postRequest,Subreddit subreddit,User user);
	
	@Mapping(target="id", source="postId")
	@Mapping(target="postName",source="postName")
	@Mapping(target="description",source="description")
	@Mapping(target= "commentCount",expression="java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	@Mapping(target="userName",source="user.username")
	@Mapping(target="subredditName",source="subreddit.name")
	public abstract PostResponse mapToDto(Post post);
	
	Integer commentCount(Post post) {return commentRepository.findByPost(post).size();}
	String getDuration(Post post) {return TimeAgo.using(post.getCreateDate().toEpochMilli()); }
	
}
