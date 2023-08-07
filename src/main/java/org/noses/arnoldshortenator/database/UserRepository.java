package org.noses.arnoldshortenator.database;

import org.noses.arnoldshortenator.security.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, String> {
}
