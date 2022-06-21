package com.tickets.tickets.repository;

import com.tickets.tickets.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUserName(String username);
}
