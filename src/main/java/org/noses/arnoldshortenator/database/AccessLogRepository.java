package org.noses.arnoldshortenator.database;

import org.springframework.data.repository.CrudRepository;

public interface AccessLogRepository extends CrudRepository<AccessLog, String>  {
}
