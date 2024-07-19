package com.tawasalna.business.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationPeriod {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endDate;
}
