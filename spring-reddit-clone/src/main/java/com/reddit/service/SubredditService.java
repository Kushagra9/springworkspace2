package com.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddit.dto.SubredditDto;
import com.reddit.exception.SpringRedditException;
import com.reddit.mapper.SubredditMapper;
//import com.reddit.mapper.SubredditMapper;
import com.reddit.model.Subreddit;
import com.reddit.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	private final SubredditRepository subredditRepository;
	@Autowired
	private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditDto save(SubredditDto subredditDTO) {
		// Subreddit mapSubreddit = mapSubredditDTO(subredditDTO);
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDTO));
//		Subreddit save = subredditRepository.save(mapSubreddit);
		subredditDTO.setId(save.getId());
		return subredditDTO;
	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID " + id));
		return subredditMapper.mapSubredditToDto(subreddit);
		// return mapToDto(subreddit);

	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		// return
		// subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());

		return subredditRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private SubredditDto mapToDto(Subreddit subreddit) {
		return subredditMapper.mapSubredditToDto(subreddit);
		// return
		// SubredditDto.builder().id(subreddit.getId()).description(subreddit.getDescription()).build();
	}

	/*
	 * private Subreddit mapSubredditDTO(SubredditDto subredditDTO) { return
	 * subredditMapper.mapDtoToSubreddit(subredditDTO); //return
	 * Subreddit.builder().name(subredditDTO.getName()).description(subredditDTO.
	 * getDescription()).build(); }
	 */

}
