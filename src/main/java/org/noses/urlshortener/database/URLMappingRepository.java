package org.noses.urlshortener.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLMappingRepository extends CrudRepository<URLMapping, String> {

    public List<URLMapping> findBySlugLike(String slugLike);
}
