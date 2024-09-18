package com.web.personalstudy.repository;

import com.web.personalstudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(String email);

    User findByIdOrElseThrow(Long assigneeId);
}
