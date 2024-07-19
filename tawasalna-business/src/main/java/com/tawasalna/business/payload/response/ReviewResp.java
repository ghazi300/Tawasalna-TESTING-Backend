package com.tawasalna.business.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResp {

    private String id;

    private String content;

    private Integer numberOfStars;

    private LocalDateTime reviewedAt;

    private LocalDateTime updatedAt;

    private String authorId;

    private String authorName;
}
