package org.noses.urlshortener.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLMappingRepository extends CrudRepository<URLMapping, String> {
}
