package com.masprogtechs.park.api.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageableDto {

    private List content = new ArrayList<>();

    private boolean first;

    private boolean last;
    @JsonProperty("page")
    private int number;

    private int size;
    @JsonProperty("pageElements")
    private int numberOfElement;

    private int totalPages;

    private int totalElements;


}
