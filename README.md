# HttpCache extension for Spring MVC

The HttpCache extension for Spring MVC is a library which aim to extends Spring MVC in order to make easier http caching for controllers endpoints annotated with `@HttpCache`. The library contains the also [the definition of the `@HttpCache` annotation](link/to/HttpCache/source)

## How to use it

Simple steps for getting started with a classical WebMvc annotation.

### Install it

- Add to dependencies

### Anotate your controlers for http caching

- Anotate Controller methods

Now, if you run the application, you can verify that the annotated endpoints serve cache headers with resources.

### Test 

How to write tests to verify that required cache headers are available

## Http Caching : how does it work

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

## Security concerns

- private attribute for `cache-control`  directive 
- no-store attribute for `cache-control`  directive

### Public caches (CDN)
 
Client -> Browser memory cache (PRIVATE) -> Browser disk cache (PRIVATE) -> CDN (PUBLIC - SHARE) -> Reverse Proxy (PUBLIC - SHARE) -> Server

## ` vary` 

The server can put the header vary on its response to instruct the cache to store various exemplaries of a resource

`vary` lists headers. The cache stores a different exemplary of the resource for each different value of each header of the `vary` list.

For example, if the server responses the `GET /user/10`  with the `vary: accept`  header, caches will store :
- one exemplary for `GET /user/10, accept: application/json`
- an other one exemplary for `GET /user/10, accept: application/xml`

By this way, one can fill very quickly public caches

## References

- https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-caching.html#mvc-caching-cachecontrol
- https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#fixed-fields-15
- https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#header-object
- https://github.com/OAI/OpenAPI-Specification/issues/2784
- [RFC 7234 sur le cache en http](https://datatracker.ietf.org/doc/html/rfc7234#section-2)
- [[DEVFEST Lille 2023] - #RetourAuxSources : Le cache HTTP - Hubert Sablonnière](https://www.youtube.com/watch?v=Tfag9MPb6YM) 🇫🇷