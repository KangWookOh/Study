package com.webflux.auth.domain.blog.service;

import com.webflux.auth.domain.blog.entity.Blog;
import com.webflux.auth.domain.blog.entity.BlogRepository;
import com.webflux.auth.domain.blog.payload.request.CreateBlogRequest;
import com.webflux.auth.domain.blog.payload.response.BlogListResponse;
import com.webflux.auth.domain.blog.payload.response.BlogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public Mono<Void> createBlog(CreateBlogRequest request, String email) {
        return Mono.just(
                Blog.builder()
                        .content(request.getContent())
                        .title(request.getTitle())
                        .userEmail(email)
                        .build())
                .flatMap(blogRepository::save)
                .then();
    }

    @Override
    public Mono<BlogListResponse> getBlogList(Pageable pageable) {
        Flux<BlogResponse> blogs = blogRepository.findAllBy(pageable)
                .flatMap(this::buildBlogResponse);

        return blogs.count()
                .flatMap(count -> buildBlogListResponse(count, blogs));
    }

    private Mono<BlogResponse> buildBlogResponse(Blog blog) {
        return Mono.just(
                BlogResponse.builder()
                        .id(blog.getId())
                        .title(blog.getTitle())
                        .userEmail(blog.getUserEmail())
                        .build()
        );
    }

    private Mono<BlogListResponse> buildBlogListResponse(long count, Flux<BlogResponse> blogResponseFlux) {
        return blogResponseFlux.collectList()
                .flatMap(blogResponses -> Mono.just(new BlogListResponse(count, blogResponses)));
    }

}
