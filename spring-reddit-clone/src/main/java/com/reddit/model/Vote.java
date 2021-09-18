package com.reddit.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long voteId;
	
	private VoteType voteType;
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="postId",referencedColumnName="postId")
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId",referencedColumnName="userId")
	private User user;
}
