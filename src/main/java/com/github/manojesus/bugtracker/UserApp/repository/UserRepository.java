package com.github.manojesus.bugtracker.UserApp.repository;

import com.github.manojesus.bugtracker.UserApp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<Users,Long>{
    //@Query("SELECT u FROM Users u WHERE u.name = ?1" )
    Optional<Users> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.enabled =TRUE WHERE u.email = ?1")
    void setEnable(String email);
}
