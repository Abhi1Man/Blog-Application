package com.BlogAppplicationBackend.blogAppApis.service.impl;

import com.BlogAppplicationBackend.blogAppApis.entities.Category;
import com.BlogAppplicationBackend.blogAppApis.entities.Post;
import com.BlogAppplicationBackend.blogAppApis.entities.User;
import com.BlogAppplicationBackend.blogAppApis.exceptions.ResourceNotFoundException;
import com.BlogAppplicationBackend.blogAppApis.payloads.PostDto;
import com.BlogAppplicationBackend.blogAppApis.payloads.PostResponse;
import com.BlogAppplicationBackend.blogAppApis.repositories.CategoryRepository;
import com.BlogAppplicationBackend.blogAppApis.repositories.PostRepository;
import com.BlogAppplicationBackend.blogAppApis.repositories.UserRepository;
import com.BlogAppplicationBackend.blogAppApis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserId",userId));
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setUser(user);
        post.setAddedDate(new Date());
        post.setCategory(cat);
        Post newPost = this.postRepository.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this. postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Post Id",postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).descending());
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDto = allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post Id",postId));
        PostDto postDto = this.modelMapper.map(post,PostDto.class);
        return postDto;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber,Integer pageSize) {

        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepository.findAll(p);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","User ID",userId));
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
        PostResponse postResponse = new PostResponse();
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepository.findAll(p);
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        List<Post> posts = this.postRepository.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        postResponse.setContent(postDtos);
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepository.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
