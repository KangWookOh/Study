package com.webflux.auth.domain.blog.handler;

import com.webflux.auth.domain.blog.exception.QueryParameterNotFoundException;
import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import com.webflux.auth.domain.blog.payload.response.BlogListResponse;
import com.webflux.auth.domain.blog.service.BlogService;
import com.webflux.auth.global.security.auth.facade.AuthenticationFacade;
import com.webflux.auth.global.validation.QueryParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class BlogHandler {

    private final BlogService blogService;
    private final QueryParameter param;
    private final AuthenticationFacade authenticationFacade;

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    public Mono<ServerResponse> createBlog(ServerRequest request) {
        Mono<Void> createBlogRequest = request.bodyToMono(CreateBlogRequest.class)
                .flatMap(req -> getCreateBlogResult(req, authenticationFacade.getUserEmail(request)));

        return ServerResponse.created(URI.create("/blog"))
                .body(createBlogRequest, Void.class);
    }

    public Mono<ServerResponse> getBlogList(ServerRequest request) {

        Map<String, Integer> params = new HashMap<>();
        param.getQueryParameter(request, PAGE, SIZE)
                .forEach((key, value) -> params.put(key, Integer.parseInt(value)));

        PageRequest page = PageRequest.of(params.get(PAGE), params.get(SIZE));

        Mono<BlogListResponse> blogListResponse = blogService.getBlogList(page);

        return ServerResponse.created(URI.create("/blog"))
                .body(blogListResponse, BlogListResponse.class);
    }

    private Mono<Void> getCreateBlogResult(CreateBlogRequest request, Mono<String> email) {
        return email
                .flatMap(userEmail -> blogService.createBlog(request, userEmail));
    }
    
}
