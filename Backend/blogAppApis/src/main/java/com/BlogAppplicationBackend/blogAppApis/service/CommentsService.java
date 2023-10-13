package com.BlogAppplicationBackend.blogAppApis.service;

import com.BlogAppplicationBackend.blogAppApis.payloads.CommentDto;

public interface CommentsService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
