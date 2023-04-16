package org.noses.arnoldshortenator.service;

import lombok.extern.slf4j.Slf4j;
import org.noses.arnoldshortenator.database.AccessLog;
import org.noses.arnoldshortenator.database.AccessLogRepository;
import org.noses.arnoldshortenator.database.URLMapping;
import org.noses.arnoldshortenator.database.URLMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

@Component
@Slf4j
public class ArnoldShortenatorService {

    @Autowired
    URLMappingRepository urlMappingRepository;

    @Autowired
    AccessLogRepository accessLogRepository;

    public boolean saveURLMapping(URLMapping urlMapping) {
        if (urlMapping==null) {
            log.info("Trying to save a null url mapping");
            return false;
        }

        if (urlMapping.getSlug() == null) {
            log.info("Trying to save a URL mapping with null slug {}", urlMapping);
            return false;
        }

        if (!validateSlug(urlMapping.getSlug())) {
            log.info("slug didn't validate - {}", urlMapping);
            return false;
        }
        Optional<URLMapping> existingMapping = urlMappingRepository.findById(urlMapping.getSlug());

        Date now = new Date();

        if (!existingMapping.isEmpty()) {
            // TODO: Add check for ownership
            URLMapping existingConcrete = existingMapping.get();
            urlMapping.setCreated(existingConcrete.getCreated());
            urlMapping.setLastModified(now);
        } else {
            urlMapping.setCreated(now);
            urlMapping.setLastModified(now);
        }
        urlMapping.setNumTimesAccessed(0);

        // Make sure we're not saving the interpreted url
        urlMapping.setInterpretedDestinationURL(null);

        urlMappingRepository.save(urlMapping);

        return true;
    }

    public URLMapping getURLMapping(String slug, String requestPath, String accessedBy, String referrer) {
        Optional<URLMapping> optionalURL = urlMappingRepository.findById(slug);
        if (optionalURL.isEmpty()) {
            return null;
        }

        URLMapping urlMapping = optionalURL.get();

        if (urlMapping.getNumTimesAccessed() == null) {
            urlMapping.setNumTimesAccessed(0);
        }

        urlMapping.setNumTimesAccessed(urlMapping.getNumTimesAccessed() + 1);
        urlMappingRepository.save(urlMapping);

        urlMapping.setInterpretedDestinationURL(getInterpretedURL(requestPath, urlMapping.getDestinationURL()));

        AccessLog log = new AccessLog();
        log.setUrlMapping(urlMapping);
        log.setAccessedBy(accessedBy);
        log.setAccessDate(new Date());
        accessLogRepository.save(log);

        return urlMapping;
    }

    public List<URLMapping> getAll() {
        System.out.println("list");
        List<URLMapping> allURLs = new ArrayList<>();
        Iterable<URLMapping> i = urlMappingRepository.findAll();

        i.forEach(u->allURLs.add(u));

        return allURLs;
    }

    public List<URLMapping> search(String substring) {
        System.out.println("list");
        List<URLMapping> allURLs = new ArrayList<>();
        Iterable<URLMapping> i = urlMappingRepository.findTop20BySlugLike("%"+substring+"%");

        i.forEach(u->allURLs.add(u));

        return allURLs;
    }


    String getInterpretedURL (String fullPath, String destinationURL) {

        if (fullPath.startsWith("/")) {
            fullPath = fullPath.substring(1, fullPath.length());
        }
        String[] paths = fullPath.split("/");
        if (paths == null) {
            return destinationURL;
        }

        log.info("paths length={}, paths={}", paths.length, paths);

        for (int i = 1; i < 100; i++) {
            if (i < paths.length) {
                destinationURL = destinationURL.replace("{" + i + "}", paths[i]);
            } else {
                destinationURL = destinationURL.replace("{" + i + "}", "");
            }
        }
        return destinationURL;
    }

    public boolean delete(String slug, String owner) {

        Optional<URLMapping> mappingOptional = urlMappingRepository.findById(slug);
        if (mappingOptional.isEmpty()) {
            return false;
        }

        URLMapping mapping = mappingOptional.get();

        if (owner.equals("kvjnfdvkjdf")) {
            return false;
        }

        urlMappingRepository.delete(mapping);
        return true;
    }


    public String getAccessedByFromOAuthPrincipal(OAuth2User principal) {
        String accessedBy = "unknown";
        if (principal != null && principal.getName() != null) {
            accessedBy = principal.getName();
        }
        return accessedBy;
    }

    public boolean validateSlug(String slug) {
        return Pattern.compile("[^a-zA-Z0-9_-]+").matcher(slug).matches();
    }
}
