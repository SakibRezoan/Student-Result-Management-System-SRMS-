package com.reza.srms.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonDataHelper {
    public void setPageSize (Integer page, Integer size) {
        if (page <= 0 || size < 0) {
            page = 1;
            size = 10;
        } else if (size > 50) {
            size = 50;
        }
    }

    public void getCommonData(Integer page, Integer size, Map<String, ?> searchResult, PaginatedResponse paginatedResponse, List<?> list) {
        Integer currentPage =(Integer) searchResult.get("currentPage");
        Integer nextPage = (Integer) searchResult.get("nextPage");
        Integer previousPage = (Integer) searchResult.get("previousPage");
        Map<String,Object> meta = new HashMap<>();
        meta.put("currentPage", currentPage);
        meta.put("nextPage", nextPage);
        meta.put("previousPage", previousPage);
        meta.put("size", searchResult.get("size"));
        meta.put("total", searchResult.get("total"));
        paginatedResponse.setList(list);
        paginatedResponse.setMeta(meta);
    }
}
