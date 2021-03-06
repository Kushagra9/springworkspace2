package com.reddit.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	private Long id;
	private Long postId;
	private Instant createDate;
	private String text;
	private String userName;
}
