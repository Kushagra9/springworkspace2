package com.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddit.model.Post;
import com.reddit.model.Subreddit;
import com.reddit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>  {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);

}
