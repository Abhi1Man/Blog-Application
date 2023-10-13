package com.BlogAppplicationBackend.blogAppApis.service;

import com.BlogAppplicationBackend.blogAppApis.entities.Post;
import com.BlogAppplicationBackend.blogAppApis.payloads.PostDto;
import com.BlogAppplicationBackend.blogAppApis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get All posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);
    //get Post by ID
    PostDto getPostById(Integer postId);

    //get by userId
    PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize);

    //get By CategoryID
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize);


    //search Posts
    List<PostDto> searchPosts(String keyword);
}
