package com.BlogAppplicationBackend.blogAppApis.service.impl;

import com.BlogAppplicationBackend.blogAppApis.entities.Comment;
import com.BlogAppplicationBackend.blogAppApis.entities.Post;
import com.BlogAppplicationBackend.blogAppApis.exceptions.ResourceNotFoundException;
import com.BlogAppplicationBackend.blogAppApis.payloads.CommentDto;
import com.BlogAppplicationBackend.blogAppApis.repositories.CommentRepository;
import com.BlogAppplicationBackend.blogAppApis.repositories.PostRepository;
import com.BlogAppplicationBackend.blogAppApis.service.CommentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentsService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","post Id",postId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","comment Id",commentId));
        this.commentRepository.delete(comment);
    }
}
