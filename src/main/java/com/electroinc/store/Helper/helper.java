package com.electroinc.store.Helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.electroinc.store.Dto.PageableResponse;

public class helper {
    public static <V, U> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> Entity = page.getContent();
        List<V> alluser = Entity.stream().map(object -> new ModelMapper().map(object, type))
                .collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(alluser);
        response.setPagenumber(page.getNumber());
        response.setPagesize(page.getSize());
        response.setTotalelement(page.getTotalElements());
        response.setTotalpages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
