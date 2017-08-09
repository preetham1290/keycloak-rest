package com.spaneos.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spaneos.user.model.MyAppUser;

@Repository
public interface MyAppUserRepo extends MongoRepository<MyAppUser, String> {

}
