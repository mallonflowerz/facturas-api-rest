package com.mallonflowerz.almacen.configuration.backup.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallonflowerz.almacen.configuration.backup.services.DatabaseBackupsService;
import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/backup")
@RestController
public class BackupController {

    private final DatabaseBackupsService databaseImportService;

    @GetMapping
    public ResponseEntity<Response> backupDatabase() throws IOException {
        return ResponseEntity.ok().body(databaseImportService.generarBackup());
    }

    @PostMapping("/{nameFile}")
    public ResponseEntity<Response> importSql(@PathVariable String nameFile) throws Exception {
        if (databaseImportService.importSqlFile(nameFile)){
            return ResponseEntity.ok().body(new Response("Base de datos importada correctamente"));
        }
        return ResponseEntity.status(404).body(new Response("El archivo no existe o no es valido, debe ser un archivo SQL"));
    }
}
