# Rest Template

Spring's RestTemplate is a robust, popular Java-based REST client. This Application is created for basic awareness about RestTemplate. You know it's very important topic because everywhere needs to call third party API so we need any rest client.  

### Setting up project 

**Added Dependency : **

* Spring Web
* Spring Devtools
* Spring Data JPA
* H2 Database

**Technologies : **

* Java 8
* Spring Boot

### HTTP Client

RestTemplate provides an abstraction for making RESTful HTTP requests. To send a request, first create an HttpClient from its builder. The builder can be used to configure per-client state. We are not going to any details here for http client, if you want to know go through for reference (here)[https://openjdk.java.net/groups/net/httpclient/intro.html].


### RestTemplate Constructors

The four RestTemplate constructors are listed below. The default constructor does not include any message body converters. You must add a message converter when using the default constructor

	RestTemplate();
	
	RestTemplate(boolean includeDefaultConverters);
	
	RestTemplate(ClientHttpRequestFactory requestFactory);
	
	RestTemplate(boolean includeDefaultConverters, ClientHttpRequestFactory requestFactory);

### RestTemplate Methods

There are a lots of methods support in RestTemplate client. so mainly six HTTP methods which are very  higher level methods. These methods make it easy to invoke many RESTful services and enforce REST best practices. All mainly methods are given below : 

* GET
* POST
* PUT
* DELETE
* HEAD
* OPTIONS

we are going to see all high level methods one by one. before Now I am going to create own API's to test RestTemplate client. 

### Created Own API to test RestTemplate

For the Post service, I created some Rest APi's to test and better command on RestTemplate.

	// Method : GET - Find All Post object 
	http://localhost:1111/api/post
	
	// Method : GET - Find All Post object With pagination if size more than 0
	http://localhost:1111/api/post?page=0&size=10
	
	// Method : GET - Find Post object by Id
	http://localhost:1111/api/post/1
	
	// Method : POST - Save Post object 
	http://localhost:1111/api/post 	
	
	// Method : POST - Save multiple Post object at a time
	http://localhost:1111/api/posts
	
	// Method : PUT - Update Post object
	http://localhost:1111/api/post
	
	// Method : DELETE - Delete Post object
	http://localhost:1111/api/post

Now we are able to test api's from same application.

### HTTP GET

Mostly we use get method many times. so we have to pay more attention here. I will demonstrate it by example as well. 

	public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException;
	
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException;
	
	public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException;
	
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables);
	
	public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables);
	
	public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException;

Suppose there has a scenario that is, There has a Get API which is giving many user posts, so  we want to get a response in the List of Object. That means, return type will be List<Post>. Let's see by example : 

There is a POJO class called Post.

	public class Post {
		private long id;
		private String title;
		private String body;
		private String userId;
	
		//Setter, getter and constructor here.	
	}

Now, we created a controller to fetch the response through own API. Let's see : 

	@GetMapping(value = "/api/posts")
	public List<Post> findAllPost() {
	
		String url = "http://localhost:1111/api/post";
		
		ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(url, Post[].class);
		Post[] posts = forEntity.getBody();
		
		return Arrays.asList(posts);
	}
	
**Some Get method needs to specify parameters so we can handle like :**

	Map<String, String> urlParameters = new HashMap<>();
	urlParameters.put("page", Integer.toString(page));
	urlParameters.put("pageSize", Long.toString(pageSize));
	
	String url = "http://localhost:1111/api/post?page={page}&size={size};
	ResponseEntity<Post[]> forEntity = new RestTemplate().getForEntity(url, Post[].class, urlParameters);
	List<Post> posts = Arrays.asList(forEntity.getBody());
	
**Let’s start with a simple example to query a single resource:**

	String url = "http://localhost:1111/api/post/"+id;
	ResponseEntity<Post> forEntity = new RestTemplate().getForEntity(url, Post.class);
	Post post = forEntity.getBody();

**Fetch Statues or ContentType from Headers**

	forEntity.getStatusCodeValue()
	forEntity.getHeaders().getContentType()

**If only the body is of interest, the getForObject() method can be used to query the resource directly as a Java object: **

	Post postObj = new RestTemplate().getForObject(url, Post.class, Long.toString(id));
	
**However, if you want to operate directly on the JSON string, this is also possible. If the class type is simply String.class, we get the raw JSON string:**

	String URL = "http://localhost:1111/api/post/" + id;
	String jsonString = new RestTemplate().getForObject(URL, String.class, id);
	
	ObjectMapper mapper = new ObjectMapper();
	JsonNode readTree = mapper.readTree(jsonString);

### HTTP POST Method

We can make good use of the postForObject(), postForEntity() and postForLocation() APIs.

	public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException;
	
	public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType) throws RestClientException;
	
	public URI postForLocation(URI url, Object request) throws RestClientException;
	

**postForObject()**

	private static final String COMMON_URL = "http://localhost:1111/api/post";

	HttpEntity<Post> entity = new HttpEntity<>(post);
	ResponseEntity<Post> forEntity = new RestTemplate().postForEntity(COMMON_URL, entity, Post.class);
	Post postObj = forEntity.getBody();

**postForEntity()** 

	HttpEntity<Post> entity = new HttpEntity<>(post);
	Post postObj = new RestTemplate().postForObject(COMMON_URL, entity, Post.class);

**postForLocation()**

	HttpEntity<Post> entity = new HttpEntity<>(post);
	URI location = new RestTemplate().postForLocation(COMMON_URL, entity);	

### HTTP PUT Method

	public void put(String url, Object request, Object... urlVariables) throws RestClientException;
	
	public void put(String url, Object request, Map<String, ?> urlVariables) throws RestClientException;

**Example : **	

	new RestTemplate().put(COMMON_URL +"/{id}", Post.class, Long.toString(post.getId()));

### HTTP Delete Method	

	public void delete(String url, Object... urlVariables) throws RestClientException;
	public void delete(String url, Map<String, ?> urlVariables) throws RestClientException;
	public void delete(URI url) throws RestClientException;

**Example :**

	new RestTemplate().delete(COMMON_URL + "/{id}", Long.toString(id));	
 
If you want to know about advance, please [click here](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) for reference.