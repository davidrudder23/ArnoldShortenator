package org.noses.urlshortener.database;

import org.springframework.data.repository.CrudRepository;

public interface AccessLogRepository extends CrudRepository<AccessLog, String>  {
}
