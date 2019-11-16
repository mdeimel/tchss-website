package org.tchss.repo;

import org.springframework.data.repository.CrudRepository;
import org.tchss.model.PasswordResetToken;
import org.tchss.model.User;

import java.util.Optional;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Integer> {

    public Optional<PasswordResetToken> findByUser(User user);

    public Optional<PasswordResetToken> findByToken(String token);
}
