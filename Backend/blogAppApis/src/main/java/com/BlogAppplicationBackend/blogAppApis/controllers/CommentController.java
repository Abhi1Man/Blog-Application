package com.BlogAppplicationBackend.blogAppApis.controllers;

import com.BlogAppplicationBackend.blogAppApis.payloads.ApiResponse;
import com.BlogAppplicationBackend.blogAppApis.payloads.CommentDto;
import com.BlogAppplicationBackend.blogAppApis.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentsService commentsService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
        CommentDto createdComment = this.commentsService.createComment(comment,postId);
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentsService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully.",true),HttpStatus.OK);
    }

}
