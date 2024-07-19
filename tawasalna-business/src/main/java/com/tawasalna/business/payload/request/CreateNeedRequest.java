package com.tawasalna.business.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateNeedRequest {

    private String info;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reservationDayStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reservationDayEnd;

    private List<String> categories;

    private String clientId;
}
