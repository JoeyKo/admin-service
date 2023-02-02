package com.example.admin.mapper;

import com.example.admin.model.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageToPageDTOMapper<T> {

    public PageDTO<T> pageToPageDTO(Page<T> page) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotal(page.getTotalElements());

        return pageDTO;
    }
}