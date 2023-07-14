package com.mallonflowerz.almacen.configuration.security.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.configuration.security.model.HistoryLogins;
import com.mallonflowerz.almacen.configuration.security.repositories.HistoryLoginsRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class LoginService {

    private final HistoryLoginsRepository hlRepo;

    public boolean existeEnHistorial(String email) {
        Optional<HistoryLogins> user = hlRepo.findByEmail(email);
        if (user.isPresent()) {
            return true;
        }
        return false;
    }

    public void guardarEnHistorial(String email) {
        HistoryLogins historial = HistoryLogins.builder()
                .email(email)
                .fechaInicioSesion(LocalDateTime.now())
                .build();
        hlRepo.save(historial);
    }

    public void eliminarDelHistorial(String email){
        Optional<HistoryLogins> user = hlRepo.findByEmail(email);
        if (user.isPresent()){
            hlRepo.delete(user.get());
        }
    }
}
