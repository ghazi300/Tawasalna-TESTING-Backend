package com.tawasalna.business.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tawasalna.business.models.enums.NeedStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NeedUpdateDTO {
    private String info;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reservationDayStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reservationDayEnd;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createdAt;

    private List<String> quotes;

    private List<String> categories;

    private String chosenquote;
    private NeedStatus needStatus;

    private String businessId;

    private String clientId;

    private String serviceId;
}
