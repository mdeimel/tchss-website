package org.tchss.repo;

import org.springframework.data.repository.CrudRepository;
import org.tchss.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    public Optional<User> findByEmailIgnoreCase(String email);
}
