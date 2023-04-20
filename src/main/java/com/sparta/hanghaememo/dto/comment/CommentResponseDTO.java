package com.sparta.hanghaememo.dto.comment;

import com.sparta.hanghaememo.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.username=comment.getUser().getUsername();
        this.contents=comment.getContents();
        this.createdAt=comment.getCreatedAt();
        this.modifiedAt=comment.getModifiedAt();
    }
}
