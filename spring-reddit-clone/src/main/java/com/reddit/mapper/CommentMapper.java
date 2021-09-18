package com.reddit.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.reddit.dto.CommentDTO;
import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.User;

@Mapper(componentModel="spring")
public interface CommentMapper {

	@Mapping(target="id", ignore=true)
	@Mapping(target="text",source="commentDTO.text")
	@Mapping(target="createDate",expression="java(java.time.Instant.now())")
	@Mapping(target="post",source="post")
	  Comment map(CommentDTO commentDTO,Post post,User user) ;
	 @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	 @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentDTO mapToDto(Comment comment);
}
