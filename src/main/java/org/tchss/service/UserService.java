package org.tchss.service;

import org.tchss.model.Registration;
import org.tchss.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User registerUser(User user);
    public void passwordReset(String token, String password);

    public Optional<User> findByEmail(String email);

    public void generatePasswordResetToken(String email);

    public List<Registration> getRegistrations(User user);
    public Registration addRegistration(Registration registration);
    public void deleteRegistration(User user, Integer registrationId);
}
