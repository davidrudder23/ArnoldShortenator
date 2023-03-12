package org.noses.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.noses.urlshortener.database.URLMapping;
import org.noses.urlshortener.service.URLShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Slf4j
public class URLShortenerController {

    @Autowired
    URLShortenerService service;

    @GetMapping(value = "/api/")
    public List<URLMapping> list() {
        return service.getAll();
    }

    @GetMapping(value="/api/{slug}/**")
    public URLMapping getBySlugREST(HttpServletRequest request, @PathVariable String slug) {
        log.info("slug={}", slug);
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

    @GetMapping(value="/{slug}/**")
    public ModelAndView getBySlugHttp(HttpServletRequest request, ModelMap model, @PathVariable String slug) {

        log.info("http slug={}", slug);
        String path = request.getRequestURI();
        log.info("path={}", path);
        URLMapping mapping = service.getURLMapping(slug, path);
        log.info("mapping={}", mapping);
        if (mapping == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return new ModelAndView("redirect:"+mapping.getInterpretedDestinationURL(), model);
    }

    @PostMapping("/api/add")
    public Boolean add(@RequestBody URLMapping mapping) {
        return service.saveURLMapping(mapping);
    }
}
