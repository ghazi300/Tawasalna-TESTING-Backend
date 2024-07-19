package com.tawasalna.business.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tawasalna.business.payload.request.SearchType;
import lombok.Data;

@Data
public class SearchRes {
    private String id;
    private String title;
    private String content;
    private SearchType searchType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float rating;
}
