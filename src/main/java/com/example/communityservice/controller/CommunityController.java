package com.example.communityservice.controller;

import com.example.communityservice.dto.PostDTO;
import com.example.communityservice.dto.CommentDTO;
import com.example.communityservice.model.Post;
import com.example.communityservice.model.Comment;
import com.example.communityservice.service.PostService;
import com.example.communityservice.service.CommentService;
import com.example.communityservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/community")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })public class CommunityController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    // Create a new post
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("caption") String caption,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setContent(content);
        postDTO.setCaption(caption);

        try {
            Post post = postService.createPost(postDTO, imageFile);
            return ResponseEntity.ok(new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl(), post.getCaption()));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Get a list of all posts
    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Get a specific post by ID
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    // Create a new comment
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(postId, commentDTO);
        return ResponseEntity.ok(commentDTO);
    }

    // Get all comments for a specific post
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // Like a post
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId) {
        likeService.likePost(postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    // Unlike a post
    @DeleteMapping("/posts/{postId}/like")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId) {
        likeService.unlikePost(postId);
        return ResponseEntity.ok("Post unliked successfully");
    }
}
