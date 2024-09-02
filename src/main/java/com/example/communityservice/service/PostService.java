package com.example.communityservice.service;

import com.example.communityservice.dto.PostDTO;
import com.example.communityservice.model.Post;
import com.example.communityservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    // Directory where the images will be saved
    @Value("${image.upload.dir}")
    private String imageUploadDir;

    public Post createPost(PostDTO postDTO, MultipartFile imageFile) throws IOException {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCaption(postDTO.getCaption());

        // Save the image file if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = saveImage(imageFile);
            post.setImageUrl(fileName);
        }

        return postRepository.save(post);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        // Create the upload directory if it doesn't exist
        Path uploadPath = Paths.get(imageUploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique file name and save the file
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath);

        return fileName;
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl(), post.getCaption()))
                .collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl(), post.getCaption());
    }
}
