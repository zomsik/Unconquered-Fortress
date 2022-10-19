package com.game.Server;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SavesRepository extends MongoRepository<Save, String> {

    List<Save> findByLogin(String login);
    Save findOneByLoginAndProfileNumber(String login, int profileNumber);
}
