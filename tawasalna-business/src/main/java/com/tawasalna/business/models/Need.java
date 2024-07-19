package com.tawasalna.business.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tawasalna.business.models.enums.NeedStatus;
import com.tawasalna.shared.userapi.model.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Need {

    @Id
    private String id;

    private String info;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reservationDayStart;
  
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reservationDayEnd;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createdAt;

    @DocumentReference
    private List<Quote> quotes;

    @DocumentReference
    private List<ServiceCategory> categories;

    @DocumentReference
    private Quote chosenquote;

    private NeedStatus needStatus;

    @DocumentReference
    private Users businessId;

    @DocumentReference
    private Users clientId;

    @DocumentReference
    private BusinessService serviceId;

    private Boolean isActive;
}
