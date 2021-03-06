package example.company.model.dao.api.concreteDao;

import example.company.model.dao.api.GenericDao;
import example.company.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
}
