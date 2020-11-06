package com.joel.practice.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PagedResult<T> extends ListResult<T>{
    private Long totalCount;

    public PagedResult() {
        super();
    }

    public PagedResult(Long totalCount, List<T> items) {
        super(items);
        this.totalCount = totalCount;
    }

    public PagedResult(Long totalCount, T[] items) {
        super(Arrays.asList(items));
        this.totalCount = totalCount;
    }
}
