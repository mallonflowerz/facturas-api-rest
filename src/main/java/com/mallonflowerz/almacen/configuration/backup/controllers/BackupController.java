package com.mallonflowerz.almacen.configuration.backup.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.backup.DatabaseConfig;
import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/backup")
@RestController
public class BackupController {

    private final DatabaseConfig databaseConfig;

    @GetMapping
    public ResponseEntity<Response> backupDatabase() throws IOException {
        return databaseConfig.generarBackup();
    }
}
