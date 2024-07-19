package com.tawasalna.social.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private String residentId;
    private String text;
    private Date createdAt;
}
