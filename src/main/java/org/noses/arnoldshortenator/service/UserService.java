package org.noses.arnoldshortenator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserService {

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
    }
}
