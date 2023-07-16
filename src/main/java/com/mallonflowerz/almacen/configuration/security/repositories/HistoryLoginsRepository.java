package com.mallonflowerz.almacen.configuration.security.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallonflowerz.almacen.configuration.security.model.HistoryLogins;

public interface HistoryLoginsRepository extends JpaRepository<HistoryLogins, UUID>{
    
    Optional<HistoryLogins> findByEmail(String email);

    Optional<HistoryLogins> findByToken(String token);
}
