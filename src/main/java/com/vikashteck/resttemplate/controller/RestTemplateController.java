package com.vikashteck.resttemplate.controller;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikashteck.resttemplate.domain.Post;

@RestController
@RequestMapping(value = "/api/client")
public class RestTemplateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateController.class);

	private static final String COMMON_URL = "http://localhost:1111/api/post";
	private static final String URL_FIND_ALL_WITH_PAGINATION = "http://localhost:1111/api/post?page={page}&size={pageSize}";
//	private static final String URL_FIND_BY_ID = COMMON_URL+"/{id}";

	/*
	 * Fetch All Post
	 */
	@GetMapping(value = "/posts")
	public List<Post> findAll() {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findAll()");

		ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(COMMON_URL, Post[].class);

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

		ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(URL_FIND_ALL_WITH_PAGINATION, Post[].class,
				urlParameters);

		return Arrays.asList(forEntity.getBody());
	}

	/*
	 * fetch all Posts by id
	 */
	@GetMapping(value = "/posts/{id}")
	public Post findByPost(@PathVariable("id") long id) {
		LOGGER.info("com.vikashteck.resttemplate.controller.PostController.findByPost(Long)");
		String URL_FIND_BY_ID = COMMON_URL + "/" + id;
		System.out.println(URL_FIND_BY_ID + " : " + id);
		ResponseEntity<Post> forEntity = new RestTemplate().getForEntity(URL_FIND_BY_ID, Post.class);
		Post postBody = forEntity.getBody();
		System.out.println("postBody : " + postBody);

		LOGGER.info("Status code value: " + forEntity.getStatusCodeValue());
		LOGGER.info("HTTP Header 'ContentType': " + forEntity.getHeaders().getContentType());

		// If only the body is of interest, the getForObject() method can be used to
		// query the resource directly as a Java object:
		Post postObj = new RestTemplate().getForObject(URL_FIND_BY_ID, Post.class, Long.toString(id));

		return postObj;
	}

	/*
	 * However, if you want to operate directly on the JSON string, this is also
	 * possible. If the class type is simply String.class, we get the raw JSON
	 * string:
	 */
	@GetMapping(value = "/posts/json-node/{id}")
	public JsonNode getAsJsonNode(@PathVariable("id") long id) throws Exception {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.getAsJsonNode(long)");
		String URL_FIND_BY_ID = COMMON_URL + "/" + id;
		String jsonString = new RestTemplate().getForObject(URL_FIND_BY_ID, String.class, id);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode readTree = mapper.readTree(jsonString);
		return readTree;
	}

	@PostMapping(value = "/posts-postForEntity")
	public Post postForEntity(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.postForEntity(Post)");
		HttpEntity<Post> entity = new HttpEntity<>(post);
		ResponseEntity<Post> forEntity = new RestTemplate().postForEntity(COMMON_URL, entity, Post.class);
		return forEntity.getBody();
	}

	@PostMapping(value = "/posts-postForObject")
	public Post postForObject(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.postForObject(Post)");
		HttpEntity<Post> entity = new HttpEntity<>(post);
		Post postObj = new RestTemplate().postForObject(COMMON_URL, entity, Post.class);
		return postObj;
	}

	@PostMapping(value = "/posts-postForLocation")
	public URI postForLocation(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.postForLocation(Post)");
		HttpEntity<Post> entity = new HttpEntity<>(post);
		URI location = new RestTemplate().postForLocation(COMMON_URL, entity);
		return location;
	}

	@PutMapping(value = "/posts")
	public void putMethod(@RequestBody Post post) {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.putMethod(Post)");
		new RestTemplate().put(COMMON_URL + "/{id}", Post.class, Long.toString(post.getId()));
	}

	@DeleteMapping(value = "/posts/id")
	public void deleteMethod(@PathVariable("id") long id) {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.deleteMethod(long)");
		new RestTemplate().delete(COMMON_URL + "/"+id);
	}

	/*
	* If you want to execute third party api with headers like Authorization and all.
	* @Return: return may be any Class/Object type but here I am using String class.
	* */
	@GetMapping(value = "/APIWithHeaders")
	public String APIWithHeaders() {
		LOGGER.info("com.vikashteck.resttemplate.controller.RestTemplateController.APIWithHeaders");

		// Mention the URI which you want to execute.
		String uri = "http://vikash@teck/api/master/session";

		// Create HttpHeaders object to set headers.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer ****************************");

		// Create HttpEntity object to get headers or any object as parameters or body and assign to RestTemplate Object
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		System.out.println("------------------------------");

		// Create RestTemplate Object and execute exchange method to get ResponseEntity object.
		ResponseEntity<String> respEntity =new RestTemplate().exchange(uri, HttpMethod.GET, entity, String.class);

		return respEntity.getBody();
	}
}
