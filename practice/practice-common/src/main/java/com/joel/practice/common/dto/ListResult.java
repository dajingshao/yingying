package com.joel.practice.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListResult<T> {
    private List<T> items;

    public ListResult() { }

    public ListResult(List<T> items) {
        this.items = items;
    }
}
