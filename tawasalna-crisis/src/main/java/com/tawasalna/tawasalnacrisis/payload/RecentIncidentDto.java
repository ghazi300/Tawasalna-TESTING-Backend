package com.tawasalna.tawasalnacrisis.payload;

import com.tawasalna.tawasalnacrisis.models.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecentIncidentDto {
    String id;
    String title;
    Status status;
}
