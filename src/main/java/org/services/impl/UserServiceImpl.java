package org.services.impl;

import org.core.UserSessionManager;
import org.entities.user.User;
import org.repositories.UserRepository;
import org.services.UserService;

import java.util.List;

import static org.common.LogMessages.*;
import static org.common.SystemErrors.*;


public class UserServiceImpl implements UserService {

    private final UserSessionManager sessionManager;
    private final UserRepository userRepository;

    public UserServiceImpl(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.userRepository = new UserRepository();
    }

    @Override
    public String login(String username, String password) {

        if (sessionManager.hasActiveSession()) {
            User activeUser = sessionManager.getActiveSession();
            throw new IllegalStateException(USER_ALREADY_LOGGED_IN.formatted(activeUser.getUsername()));
        }

        User user = userRepository.getAll()
                                  .stream()
                                  .filter(u -> u.getUsername().equals(username)
                                          && u.getPassword().equals(password)
                                         )
                                  .findFirst()
                                  .orElseThrow(() -> new IllegalArgumentException(INCORRECT_LOGIN_CREDENTIALS));

        sessionManager.setActiveSession(user);

        return SUCCESSFULLY_LOGGED_IN.formatted(user.getUsername());
    }

    @Override
    public String register(String username, String password) {

        boolean isUsernameAlreadyExist = userRepository.getAll()
                                                       .stream()
                                                       .anyMatch(u -> u.getUsername().equals(username));
        if (isUsernameAlreadyExist) {
            throw new IllegalArgumentException(SUCH_USERNAME_ALREADY_EXIST.formatted(username));
        }

        User user = new User(username, password);
        userRepository.save(user.getId(), user);

        return SUCCESSFULLY_REGISTERED.formatted(user.getUsername());
    }

    @Override
    public String logout() {

        if (!sessionManager.hasActiveSession()) {
            throw new IllegalStateException(NO_ACTIVE_USER_SESSION_FOUND);
        }

        User activeUser = sessionManager.getActiveSession();
        sessionManager.terminateActiveSession();

        return SUCCESSFULLY_LOGGED_OUT.formatted(activeUser.getUsername());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
