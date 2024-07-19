package com.tawasalna.business.payload.request;

import com.tawasalna.business.models.enums.ReviewMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponseDTO {

    private String content;
    private ReviewMode mode;
    private String author;
}
