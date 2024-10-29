# Http caching filter for jakarta servlet applications 

The Http caching filter for jakarta servlet applications is a library which aim to enable http caching for jakarta servlet applications according to http caching
information provided by an openAPI specification.

## How to use it [WIP]

Simple steps for getting started with a classical jakarta servlet application.

### Install it

- Add to dependencies

### Add a openAPI specification in the classpath

Write a openAPI specification file in the ` src/main/resources` of your project with `x-cache` extensions inside responses code.

Now, if you run the application, you can verify that the annotated endpoints serve cache headers with resources.

### Test 

How to write tests to verify that required cache headers are available

## Http Caching : how does it work [WIP]

### What is http caching and why it is relevant for APIs

The main principle of http cache is that a web client can save data exchange over the network if a resource it needs has not changed since the last time it got it from the server :
- client can save a whole http request to get again a resource, while the stored resource is fresh, if the server gave a peremption date for the resource at the first request : this can be done using `cache-control` header
- client can save data exchange to get again a resource if the client received a hash for resource from the server and the server answers that the hash has not changed when the client requests again the resource : this can be done using `etag`  and `cache-control` headers
- client can also save data exchange if it knows the resource has not been modified since it last got it : this can be done with headers `last-modified` ...

API resources can also benefit http caching because, whereas they can be often modified, business rules can specify for few of them how long they can be stored and client can always asks servers if hash of resources changed, avoiding to send an other time the resource through the network in the same state. So http caching for API is relevant, in particular with `etag` and `cache-control` headers. Therefore, API servers should serve resources with this headers

### Http caching

A simple example explained 

### More about cache directives

- cache-control directives for clients
- cache-control directives for server
- obsolete directives

## More about the lib 

WIP

## Security concerns [WIP]

- private attribute for `cache-control`  directive 
- no-store attribute for `cache-control`  directive

### Public caches (CDN)
 
Client -> Browser memory cache (PRIVATE) -> Browser disk cache (PRIVATE) -> CDN (PUBLIC - SHARE) -> Reverse Proxy (PUBLIC - SHARE) -> Server

## ` vary` [WIP]

The server can put the header vary on its response to instruct the cache to store various exemplaries of a resource

`vary` lists headers. The cache stores a different exemplary of the resource for each different value of each header of the `vary` list.

For example, if the server responses the `GET /user/10`  with the `vary: accept`  header, caches will store :
- one exemplary for `GET /user/10, accept: application/json`
- an other one exemplary for `GET /user/10, accept: application/xml`

By this way, one can fill very quickly public caches

## References

- https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-caching.html#mvc-caching-cachecontrol
- https://docs.spring.io/spring-framework/reference/web/webmvc/filters.html#filters-shallow-etag
- https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#fixed-fields-15
- https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#header-object
- https://github.com/OAI/OpenAPI-Specification/issues/2784
- [Hhttp fileds (headers)](https://www.iana.org/assignments/http-fields/http-fields.xhtml)
- [RFC 9111 about http caching](https://datatracker.ietf.org/doc/html/rfc9111)
- [RFC 9110 : etag](https://datatracker.ietf.org/doc/html/rfc9110#name-etag)
- [[DEVFEST Lille 2023] - #RetourAuxSources : Le cache HTTP - Hubert Sablonnière](https://www.youtube.com/watch?v=Tfag9MPb6YM) 🇫🇷
- https://blog.payara.fish/caching-rest-resources-in-jakarta-rest-formerly-jax-rs