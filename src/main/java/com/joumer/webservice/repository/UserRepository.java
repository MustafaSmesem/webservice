package com.joumer.webservice.repository;

import com.joumer.webservice.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserById(String id);
    Optional<User> findUserByUsername(String userName);

    List<User> findByEnabledTrue();

    boolean existsByUsername(String userName);
}
