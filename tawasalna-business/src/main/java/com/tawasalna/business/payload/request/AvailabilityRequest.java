package com.tawasalna.business.payload.request;

import lombok.Data;

@Data
public class AvailabilityRequest {
    private String id;
    private String dayOfWeek;
    private boolean available;
    private String startTime;
    private String endTime;

}