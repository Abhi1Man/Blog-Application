package com.BlogAppplicationBackend.blogAppApis.controllers;

import com.BlogAppplicationBackend.blogAppApis.config.AppConstants;
import com.BlogAppplicationBackend.blogAppApis.entities.Post;
import com.BlogAppplicationBackend.blogAppApis.entities.User;
import com.BlogAppplicationBackend.blogAppApis.exceptions.ResourceNotFoundException;
import com.BlogAppplicationBackend.blogAppApis.payloads.ApiResponse;
import com.BlogAppplicationBackend.blogAppApis.payloads.CategoryDto;
import com.BlogAppplicationBackend.blogAppApis.payloads.PostDto;
import com.BlogAppplicationBackend.blogAppApis.payloads.PostResponse;
import com.BlogAppplicationBackend.blogAppApis.repositories.UserRepository;
import com.BlogAppplicationBackend.blogAppApis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
        PostDto createdPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }
    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy){
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize, sortBy);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.CREATED);
    }
    //get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    //get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getByUsers(@PathVariable Integer userId, @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize){
        PostResponse posts = this.postService.getPostsByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getByCategory(@PathVariable Integer categoryId,@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize){
        PostResponse categories = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(categories,HttpStatus.OK);
    }

    //update
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        return new ResponseEntity<PostDto>(this.postService.updatePost(postDto,postId), HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted.",true);
    }

    //search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> search(@PathVariable String keyword){
        List<PostDto> postDtos = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }
}
