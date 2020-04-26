package org.tchss.repo;

import org.springframework.data.repository.CrudRepository;
import org.tchss.model.Registration;
import org.tchss.model.User;

import java.util.List;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    public List<Registration> findAllByUser(User user);
}
