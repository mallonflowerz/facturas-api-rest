package com.mallonflowerz.almacen.configuration.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import lombok.Data;

@Component
@Data
public class DatabaseConfig {

    @Value("${almacen.backup_file_path}")
    private String BACKUP_FILE_PATH;

    @Value("${almacen.username}")
    private String USERNAME;

    @Value("${almacen.password}")
    private String PASSWORD;

    @Value("${almacen.db_name}")
    private String DB_NAME;

    @Value("${almacen.mysqldump_path}")
    private String MYSQLDUMP_PATH;

    public String cambioDeFormatoArchivo() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        String nameFileNew = this.BACKUP_FILE_PATH + "\\backup_" + formattedDateTime + ".sql";
        return nameFileNew;
    }

    public boolean existePath() {
        File backupFile = new File(this.BACKUP_FILE_PATH);
        if (backupFile.exists()) {
            return true;
        }
        return false;
    }

    public ResponseEntity<Response> generarBackup() throws IOException {
        if (this.existePath()) {
            String nameFile = this.cambioDeFormatoArchivo();
            String backupCommand = this.getMYSQLDUMP_PATH() + " --user=" + this.getUSERNAME() +
                    " --password=" + this.getPASSWORD() + " --databases " + this.getDB_NAME()
                    + " > "
                    + nameFile;

            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", backupCommand);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try {
                process.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            File backupFile = new File(nameFile);
            if (backupFile.exists()) {
                Path filePath = backupFile.toPath();
                return ResponseEntity.ok()
                        .body(new Response("Copia de seguridad correctamente guardada en " + filePath));
            } else {
                return ResponseEntity.status(404).body(new Response("Error: El archivo no existe"));
            }
        }
        return ResponseEntity.status(404).body(
                new Response(String.format("El path %s no existe", this.getBACKUP_FILE_PATH())));
    }
}
