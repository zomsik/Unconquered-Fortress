package com.game.Server;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
    User findFirstByLogin(String login);

    User findFirstByEmail(String email);
}