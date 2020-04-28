package com.vikashteck.resttemplate.domain;

public class Post {
	
	private long id;
	private String title;
	private String body;
	private String userId;
	
	public Post() {	}

	public Post(long id, String title, String body, String userId) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", body=" + body + ", userId=" + userId + "]";
	}
}
