package com.mallonflowerz.almacen.configuration.security.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.configuration.backup.DatabaseConfig;
import com.mallonflowerz.almacen.configuration.security.model.HistoryLogins;
import com.mallonflowerz.almacen.configuration.security.repositories.HistoryLoginsRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class LoginService {

    private final HistoryLoginsRepository hlRepo;
    private final DatabaseConfig databaseConfig;

    public boolean existeEnHistorial(String email) {
        Optional<HistoryLogins> user = hlRepo.findByEmail(email);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean limiteAlcanzado(){
        if (hlRepo.count() >= databaseConfig.getLIMIT_DEVICES()){
            return true;
        }
        return false;
    }

    public void guardarEnHistorial(String email, String token) {
        HistoryLogins historial = HistoryLogins.builder()
                .email(email)
                .fechaInicioSesion(LocalDateTime.now())
                .token(token)
                .build();
        hlRepo.save(historial);
    }

    public boolean eliminarForzoso(String email){
        Optional<HistoryLogins> user = hlRepo.findByEmail(email);
        if (user.isPresent()) {
            hlRepo.delete(user.get());
            return true;
        }
        return false;
    }

    public boolean eliminarDelHistorial(String token){
        Optional<HistoryLogins> user = hlRepo.findByToken(token);
        if (user.isPresent()){
            hlRepo.delete(user.get());
            return true;
        }
        return false;
    }
}
