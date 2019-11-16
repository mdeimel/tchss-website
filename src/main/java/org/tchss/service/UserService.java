package org.tchss.service;

import org.tchss.model.User;

import java.util.Optional;

public interface UserService {

    public User registerUser(User user);
    public void passwordReset(String token, String password);

    public Optional<User> findByEmail(String email);

    public void generatePasswordResetToken(String email);
}
