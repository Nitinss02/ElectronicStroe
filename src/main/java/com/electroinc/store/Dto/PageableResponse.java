package com.electroinc.store.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T> {
    private List<T> content;
    private int pagenumber;
    private int pagesize;
    private long Totalelement;
    private int totalpages;
    private boolean lastPage;
}
