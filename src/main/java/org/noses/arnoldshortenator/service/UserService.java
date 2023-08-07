package org.noses.arnoldshortenator.service;

import lombok.extern.slf4j.Slf4j;
import org.noses.arnoldshortenator.database.UserRepository;
import org.noses.arnoldshortenator.security.Provider;
import org.noses.arnoldshortenator.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

/*    @Autowired
    User
    public void processOAuthPostLogin(String username) {
        User existUser = repo.getUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            repo.save(newUser);
        }

    }
 */

    public void processOAuthPostLogin(String username) {
        log.info("Post-process OAuth for {}", username);
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEmail(username);
            userRepository.save(newUser);
        }
    }
}
