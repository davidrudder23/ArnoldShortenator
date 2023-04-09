package org.noses.arnoldshortenator.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.noses.arnoldshortenator.database.URLMapping;
import org.noses.arnoldshortenator.service.ArnoldShortenatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class WebController {

    @Autowired
    ArnoldShortenatorService service;

    @GetMapping(value="/index.html")
    public ModelAndView indexHtml() {
        return new ModelAndView("index.html");
    }
    @GetMapping(value="/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @GetMapping(value="/static/{page}")
    public ModelAndView staticPage(HttpServletRequest request, @PathVariable String page) {
        return new ModelAndView(page);
    }


    //    @GetMapping(value="/{slug:^.+(?!static|logout|user)}/**", produces="text/html")
    @GetMapping(value="/{slug}/**")
    public ModelAndView getBySlug(HttpServletRequest request, ModelMap model, @AuthenticationPrincipal OAuth2User principal, @PathVariable String slug) {

        log.info("http slug={}", slug);
        log.info("principal={}", principal);

        String path = request.getRequestURI();
        log.info("path={}", path);

        URLMapping mapping = service.getURLMapping(slug, path, service.getAccessedByFromOAuthPrincipal(principal), request.getHeader("Referer"));
        log.info("mapping={}", mapping);
        if (mapping == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return new ModelAndView("redirect:"+mapping.getInterpretedDestinationURL(), model);
    }

}
