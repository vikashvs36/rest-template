package com.vikashteck.resttemplate.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikashteck.resttemplate.domain.Post;

@RestController
@RequestMapping(value = "/api")
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	/*
	 * Fetch All Post
	 */
	@GetMapping(value = "/posts")
	public List<Post> findAll() {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findAll()");

		String url = "https://jsonplaceholder.typicode.com/posts";
		ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(url, Post[].class);

		return Arrays.asList(forEntity.getBody());
	}

	/*
	 * fetch all Posts with pagination
	 */
	@GetMapping(value = "/posts/page/{page}/pageSize/{pageSize}")
	public List<Post> findAll(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findAll(int, int)");

		Map<String, String> urlParameters = new HashMap<>();
		urlParameters.put("page", Integer.toString(page));
		urlParameters.put("pageSize", Long.toString(pageSize));

		String url = "https://jsonplaceholder.typicode.com/posts?page=" + page + "&pageSize=" + pageSize;
		ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(url, Post[].class, urlParameters);

		return Arrays.asList(forEntity.getBody());
	}

	/*
	 * fetch all Posts by id
	 */
	@GetMapping(value = "/posts/{id}")
	public Post findByPost(@PathVariable("id") Long id) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findByPost(Long)");

		String url = "https://jsonplaceholder.typicode.com/posts/" + id;
		ResponseEntity<Post> forEntity = new RestTemplate().getForEntity(url, Post.class);
		Post postBody = forEntity.getBody();

		LOGGER.info("Status code value: " + forEntity.getStatusCodeValue());
		LOGGER.info("HTTP Header 'ContentType': " + forEntity.getHeaders().getContentType());

		// If only the body is of interest, the getForObject() method can be used to
		// query the resource directly as a Java object:
		Post postObj = new RestTemplate().getForObject(url, Post.class, Long.toString(id));

		return postObj;
	}

	/*
	 * However, if you want to operate directly on the JSON string, this is also
	 * possible. If the class type is simply String.class, we get the raw JSON
	 * string:
	 */
	@GetMapping(value = "/posts/post/{id}")
	public JsonNode getAsJsonNode(@PathVariable("id") long id) throws IOException {
		String url = "https://jsonplaceholder.typicode.com/posts/" + id;
		String jsonString = new RestTemplate().getForObject(url, String.class, id);
		System.out.println(jsonString);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode readTree = mapper.readTree(jsonString);
		return readTree;
	}

}
