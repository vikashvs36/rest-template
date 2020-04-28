package com.vikashteck.resttemplate.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.vikashteck.resttemplate.domain.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> { }
