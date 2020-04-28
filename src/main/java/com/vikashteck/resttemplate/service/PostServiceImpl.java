package com.vikashteck.resttemplate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vikashteck.resttemplate.domain.Post;
import com.vikashteck.resttemplate.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public List<Post> findAll() {
		List<Post> postList = new ArrayList<>();
		
		Iterable<Post> postObj = postRepository.findAll();
		postObj.forEach(postList::add);
		
		return postList;
	}

	@Override
	public List<Post> findAll(int page, int pageCount) {
		Pageable pageable = PageRequest.of(page, pageCount);
		Page<Post> allObjList = postRepository.findAll(pageable);
		return allObjList.hasContent() ? allObjList.getContent() : new ArrayList<Post>();
	}

	@Override
	public Post findById(long id) throws Exception {
		Optional<Post> optional = postRepository.findById(id);
		return optional.isPresent() ? optional.get() : new Post();
	}

	@Override
	public Post save(Post post) throws Exception {
		return postRepository.save(post);
	}
	
	@Override
	public Iterable<Post> saveAll(List<Post> entities) throws Exception {
		return postRepository.saveAll(entities);
	}

	@Override
	public Post update(Post post) throws Exception {
		return postRepository.save(post);
	}

	@Override
	public void delete(Post post) throws Exception {
		postRepository.delete(post);
	}

}
