package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {

    public PageDTO(Page<T> page){
        this.content = page.getContent();
        this.total = page.getTotalElements();
    }

    private List<T> content;

    private long total;
}