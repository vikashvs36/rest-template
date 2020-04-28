package com.vikashteck.resttemplate.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vikashteck.resttemplate.domain.Post;
import com.vikashteck.resttemplate.service.PostService;

@RestController
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@GetMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Post> findAll(@PathParam("page") int page, @PathParam("size") int size) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findAll(int, int)");
		List<Post> posts = null;
		try {
			if (page >= 0 && size > 0) {
				System.out.println(page + " - " + size);
				posts = postService.findAll(page, size);
			} else
				posts = postService.findAll();
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getMessage());
		}
		return posts;
	}

	@GetMapping(value = "/api/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Post findById(@PathVariable("id") long id) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findById(long)");
		try {
			return postService.findById(id);
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getMessage());
			e.printStackTrace();
		}
		return new Post();
	}

	@PostMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
	public Post save(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.save(Post)");
		try {
			return postService.save(post);
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getMessage());
			e.printStackTrace();
		}
		return new Post();
	}

	@PostMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Post> saveAll(@RequestBody List<Post> postList) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.saveAll(List<Post>)");
		List<Post> posts = new ArrayList<>();
		try {
			Iterable<Post> entries = postService.saveAll(postList);
			entries.forEach(posts::add);
			return posts;
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getMessage());
		}
		return posts;
	}

	@PutMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
	public Post update(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.saveAll(List<Post>)");
		try {
			return postService.update(post);
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getMessage());
		}
		return new Post();
	}

	@DeleteMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.saveAll(List<Post>)");
		try {
			postService.delete(post);
		} catch (Exception e) {
			LOGGER.error("Error : " + e.getMessage());
		}
	}

}
