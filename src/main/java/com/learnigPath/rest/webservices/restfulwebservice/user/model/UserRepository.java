package com.learnigPath.rest.webservices.restfulwebservice.user.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
