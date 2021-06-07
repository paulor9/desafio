package com.pra.desafio.controller;

import com.pra.desafio.dto.CategoriesDTO;
import com.pra.desafio.service.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriesController {

    private final CategoriesService categoriesService;

    Logger logger = LoggerFactory.getLogger(CategoriesController.class);

    public CategoriesController( CategoriesService categoriesService    ) {
        this.categoriesService = categoriesService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/v1/categories")
    public List<CategoriesDTO> all() {
        return  categoriesService.listAllCategories();
    }
}
