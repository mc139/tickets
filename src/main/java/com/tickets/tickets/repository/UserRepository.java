package com.tickets.tickets.repository;

import com.tickets.tickets.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUserName(String username);
}
