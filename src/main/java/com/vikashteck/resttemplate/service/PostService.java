package com.vikashteck.resttemplate.service;

import java.util.List;

import com.vikashteck.resttemplate.domain.Post;

public interface PostService {
	
	List<Post> findAll();
	
	List<Post> findAll(int page, int pageCount);
	
	Post findById(long id) throws Exception;
	
	Iterable<Post> saveAll(List<Post> postList) throws Exception;
	
	Post save(Post post) throws Exception;
	
	Post update(Post post) throws Exception;
	
	void delete(Post post) throws Exception;

}
