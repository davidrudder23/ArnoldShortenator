package org.noses.urlshortener.service;

import lombok.extern.slf4j.Slf4j;
import org.noses.urlshortener.database.URLMapping;
import org.noses.urlshortener.database.URLMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class URLShortenerService {

    @Autowired
    URLMappingRepository repository;
    public boolean saveURLMapping(URLMapping urlMapping) {
        if (urlMapping==null) {
            log.info("Trying to save a null url mapping");
            return false;
        }

        if (urlMapping.getSlug() == null) {
            log.info("Trying to save a URL mapping with null slug {}", urlMapping);
            return false;
        }
        Optional<URLMapping> existingMapping = repository.findById(urlMapping.getSlug());

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

        repository.save(urlMapping);

        return true;
    }

    public URLMapping getURLMapping(String slug) {
        Optional<URLMapping> optionalURL = repository.findById(slug);
        if (optionalURL.isEmpty()) {
            return null;
        }

        URLMapping urlMapping = optionalURL.get();

        if (urlMapping.getNumTimesAccessed() == null) {
            urlMapping.setNumTimesAccessed(0);
        }

        urlMapping.setNumTimesAccessed(urlMapping.getNumTimesAccessed() + 1);
        repository.save(urlMapping);

        return urlMapping;
    }

    public List<URLMapping> getAll() {
        System.out.println("list");
        List<URLMapping> allURLs = new ArrayList<>();
        Iterable<URLMapping> i = repository.findAll();

        i.forEach(u->allURLs.add(u));

        return allURLs;
    }
}
