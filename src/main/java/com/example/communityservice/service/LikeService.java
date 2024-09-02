package com.example.communityservice.service;

import com.example.communityservice.model.Like;
import com.example.communityservice.model.Post;
import com.example.communityservice.repository.LikeRepository;
import com.example.communityservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    public void likePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (!likeRepository.existsByPostId(postId)) {
            Like like = new Like();
            like.setPost(post);
            likeRepository.save(like);
        }
    }

    public void unlikePost(Long postId) {
        Like like = likeRepository.findByPostId(postId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);
    }
}
