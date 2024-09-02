package com.example.communityservice.service;

import com.example.communityservice.dto.CommentDTO;
import com.example.communityservice.model.Comment;
import com.example.communityservice.model.Post;
import com.example.communityservice.repository.CommentRepository;
import com.example.communityservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment createComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getContent()))
                .collect(Collectors.toList());
    }
}
