package com.github.manojesus.bugtracker.registration.token.repository;

import com.github.manojesus.bugtracker.UserApp.entity.Users;
import com.github.manojesus.bugtracker.registration.token.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    @Query(value = "SELECT t FROM ConfirmationToken t WHERE t.user=?1")
    Optional<ConfirmationToken> findByToken(Users user);
    @Query(value = "SELECT t FROM ConfirmationToken t WHERE t.token=?1")
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "UPDATE ConfirmationToken t SET t.confirmedAt = ?2 WHERE t.token = ?1")
    int updateConfirmDate(String token, LocalDateTime confirmedAt);
}
