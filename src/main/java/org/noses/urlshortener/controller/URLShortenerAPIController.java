package org.noses.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.noses.urlshortener.database.URLMapping;
import org.noses.urlshortener.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class URLShortenerAPIController {

    @Autowired
    URLShortenerService service;

    @GetMapping(value = "/")
    public List<URLMapping> list() {
        return service.getAll();
    }
    @GetMapping(value="/{slug:^.*(?!logout|user)}/**")
    public URLMapping getBySlug(HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal, @PathVariable String slug) {
        log.info("slug={}", slug);
        log.info("principal={}", principal);
        String path = request.getRequestURI();
        log.info("path={}", path);

        URLMapping mapping = service.getURLMapping(slug, path);
        if (mapping == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }

        return mapping;
    }

    @GetMapping(value="/search/{slug}")
    public List<URLMapping> search (HttpServletRequest request, @PathVariable String slug) {
        if (slug.length()<3) {
            return new ArrayList<>();
        }

        List<URLMapping> mappings = service.search(slug);
        return mappings;
    }

    @PostMapping(value="/add", produces="application/json")
    public Boolean add(@RequestBody URLMapping mapping) {
        return service.saveURLMapping(mapping);
    }
}
