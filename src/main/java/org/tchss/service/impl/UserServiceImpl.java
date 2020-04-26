package org.tchss.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.tchss.exception.AuthenticationException;
import org.tchss.model.PasswordResetToken;
import org.tchss.model.Registration;
import org.tchss.model.User;
import org.tchss.repo.PasswordResetTokenRepository;
import org.tchss.repo.RegistrationRepository;
import org.tchss.repo.UserRepository;
import org.tchss.service.NotificationService;
import org.tchss.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        // Check if email address is already used.
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new AuthenticationException("Email address already in use");
        }

        // Encode password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public void passwordReset(String token, String password) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthenticationException("Invalid reset token"));

        User user = resetToken.getUser();

        // The comparison will be -1 if the expiry date is in the future, and
        // 1 if the expiry date is in the past.
        if (resetToken.getExpiryDate().compareTo(new Date()) < 0) {
            passwordResetTokenRepository.delete(resetToken);
            log.info("Password reset email expired for user {}", user.getEmail());
            throw new AuthenticationException("Password reset email link has expired");
        }

        // Encode password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void generatePasswordResetToken(String email) {
        // First check to see if an existing reset token exists for the user.
        User user = userRepository.findByEmailIgnoreCase(email).orElse(null);
        // If the user can't be found, don't do anything, just ignore it.
        if (user == null) {
            log.info("Password reset attempt for email {}, but no matching user found", email);
            return;
        }

        PasswordResetToken resetToken = passwordResetTokenRepository.findByUser(user)
                .orElse(null);
        if (resetToken == null) {
            resetToken = new PasswordResetToken(user);
            passwordResetTokenRepository.save(resetToken);
        }

        // TODO Change to property file
        String url = "localhost:8080";
        String link = url + "/password-reset/"+resetToken.getToken();
        String message = "Please go to the following link to reset your password " + link;

        notificationService.sendEmail("TCHSS Password Reset", email, message);
    }

    @Override
    public List<Registration> getRegistrations(User user) {
        return registrationRepository.findAllByUser(user);
    }

    @Override
    public Registration addRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public void deleteRegistration(User user, Integer registrationId) {
        Registration registration = registrationRepository.
                findById(registrationId).orElse(null);
        if (registration != null && registration.getUser().equals(user)) {
            registrationRepository.delete(registration);
        }
    }
}
