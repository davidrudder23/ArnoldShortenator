package org.noses.urlshortener.controller;

import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.noses.urlshortener.database.URLMapping;
import org.noses.urlshortener.database.URLMappingRepository;
import org.noses.urlshortener.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class URLShortener {

    @Autowired
    URLShortenerService service;

    @GetMapping("/")
    public List<URLMapping> list() {
        return service.getAll();
    }

    @GetMapping("/{slug}")
    public URLMapping getBySlug(@PathVariable String slug) {
        URLMapping mapping = service.getURLMapping(slug);
        if (mapping == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }

        return mapping;
    }

    @PostMapping("/add")
    public Boolean add(@RequestBody URLMapping mapping) {
        return service.saveURLMapping(mapping);
    }
}
