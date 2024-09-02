package com.example.communityservice.dto;

public class CommentDTO {
    private Long id;
    private String content;

    public CommentDTO(Long id, String content) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
// Constructors, getters, setters
}
