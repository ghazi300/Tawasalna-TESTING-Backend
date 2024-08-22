package com.ipactconsult.tawasalnabackendapp.payload.response;

import com.ipactconsult.tawasalnabackendapp.models.CategoryEvent;

import com.ipactconsult.tawasalnabackendapp.models.StatusEvent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter


public class EventResponse {
     String id;
     String title;
     String description;
     LocalDateTime startDate;
     LocalDateTime endDate;
     String location;
     CategoryEvent category;
     StatusEvent status;
     String createdBy;
     LocalDateTime createdDate;
     String lastModifiedBy;
     LocalDateTime lastModifiedDate;
      String imageId;
     int participationCount;
     List<String> participants;
     int likeCount;
     List<String> likes;
}
