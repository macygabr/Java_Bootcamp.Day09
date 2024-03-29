package edu.school21.sockets.repositories;

import java.util.Optional;
import edu.school21.sockets.models.User;

public interface UsersRepository extends CrudRepository<User> {
    <T> Optional<T> findByName(String email);
}