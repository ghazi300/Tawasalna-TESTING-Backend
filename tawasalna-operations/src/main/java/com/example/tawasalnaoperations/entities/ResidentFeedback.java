package com.example.tawasalnaoperations.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "residentFeedbacks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidentFeedback {
    @Id
    private String feedbackId;
    private String residentId;
    private Date feedbackDate;
    private String comments;
   // private int rating;

}
