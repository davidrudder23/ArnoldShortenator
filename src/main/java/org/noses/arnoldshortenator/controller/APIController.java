package org.noses.arnoldshortenator.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.noses.arnoldshortenator.database.URLMapping;
import org.noses.arnoldshortenator.service.ArnoldShortenatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/*@RestController
@RequestMapping(value="api", produces="application/json")
*/
@Slf4j
public class APIController {

    @Autowired
    ArnoldShortenatorService service;

    @GetMapping(value="/{slug:^.*(?!logout|user|login.html)}/**")
    public URLMapping getBySlug(HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal, @PathVariable String slug) {
        log.info("slug={}", slug);
        log.info("principal={}", principal);
        String path = request.getRequestURI();
        log.info("path={}", path);

        URLMapping mapping = service.getURLMapping(slug, path, service.getAccessedByFromOAuthPrincipal(principal), request.getHeader("Referer"));
        if (mapping == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }

        return mapping;
    }

    @GetMapping(value="/search/{slug}")
    public List<URLMapping> search (HttpServletRequest request, @PathVariable String slug) {
        if (slug.length()<1) {
            return new ArrayList<>();
        }

        List<URLMapping> mappings = service.search(slug);
        return mappings;
    }

    @PostMapping
    public Boolean add(@RequestBody URLMapping mapping) {
        return service.saveURLMapping(mapping);
    }

    @DeleteMapping(value="/{slug}")
    public Boolean delete(HttpServletRequest request, @AuthenticationPrincipal OAuth2User principal, @PathVariable String slug) {
        return service.delete(slug, service.getAccessedByFromOAuthPrincipal(principal));
    }
}
