package com.reddit.dto;

import java.time.Instant;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.reddit.model.Post;
import com.reddit.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
 
	private Long id;
	private String subredditName;
	private String postName;
	private String url;
	private String description;	
	private String userName;
	private Integer voteCount;
	private Integer commentCount;
	private String duration;
 
}
