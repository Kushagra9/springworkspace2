package com.reddit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.Subreddit;
import com.reddit.model.User;
import com.reddit.model.Vote;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>  {

	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);

}
