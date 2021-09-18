package com.reddit.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.reddit.dto.VoteDTO;
import com.reddit.exception.PostNotFoundException;
import com.reddit.exception.SpringRedditException;
import com.reddit.model.Post;
import com.reddit.model.Vote;
import com.reddit.model.VoteType;
import com.reddit.repository.PostRepository;
import com.reddit.repository.VoteRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor

public class VoteService {
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	@Transactional
	public void vote(VoteDTO voteDto) {
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()->new PostNotFoundException("Post Not Found With ID -"+voteDto.getPostId()));
		//recent vote by the user
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,authService.getCurrentUser());;
		//if upvote than  user can doenvote and if downvote than user can upvote
		//vote at same direction twice is not allowed
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
			throw new SpringRedditException("You have already "+voteDto.getVoteType()+"'d for this post ");
			
		}
		if(VoteType.UPVOTE.equals(voteDto.getVoteType()))
				post.setVoteCount(post.getVoteCount()+1);
		else {
			post.setVoteCount(post.getVoteCount()-1);
		}
		voteRepository.save(mapToVote(voteDto, post));
	}
	//for single mapper no need to make mapper
	
	  private Vote mapToVote(VoteDTO voteDto, Post post) {
	        return Vote.builder()
	                .voteType(voteDto.getVoteType())
	                .post(post)
	                .user(authService.getCurrentUser())
	                .build();
	    }

}
