package com.reddit.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import org.hibernate.annotations.GeneratorType;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long postId;
	
	@NotBlank(message="Post Name cannot be empty or Null")
	private String postName;
	@Nullable
	private String url;
	@Nullable
	@Lob
	private String description;
	@Builder.Default
	private Integer voteCount=0;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId",referencedColumnName="userId")
	private User user;
	@Column(name="created_date")
	private Instant createDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id", referencedColumnName="id")
	private Subreddit subreddit;


}
