package com.vikashteck.resttemplate.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.vikashteck.resttemplate.domain.Post;

@RestController
@RequestMapping(value = "/api")
public class PostController {
	
	@GetMapping(value = "/posts")
	public List<Post> findAllPost() {
		String url = "https://jsonplaceholder.typicode.com/posts";
		ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(url, Post[].class);
		return Arrays.asList(forEntity.getBody());
	}

}
