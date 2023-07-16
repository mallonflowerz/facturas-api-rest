package com.mallonflowerz.almacen.configuration.backup.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.mallonflowerz.almacen.configuration.backup.DatabaseConfig;
import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DatabaseBackupsService {

    private final DatabaseConfig databaseConfig;

    public boolean importSqlFile(String fileName) throws Exception {
        String filePath = databaseConfig.getBACKUP_FILE_PATH() + "\\" + fileName;
        if (this.existePath(filePath) && fileName.endsWith(".sql")) {
            String commandImport = String.format("%s\\mysql --user=%s --password=%s --databases %s < %s",
                    databaseConfig.getMYSQL_PATH(), databaseConfig.getUSERNAME(),
                    databaseConfig.getPASSWORD(), databaseConfig.getDB_NAME(), filePath);
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", commandImport);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try {
                process.waitFor();
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    public String cambioDeFormatoArchivo() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        String nameFileNew = databaseConfig.getBACKUP_FILE_PATH() + "\\backup_" + formattedDateTime + ".sql";
        return nameFileNew;
    }

    public boolean existePath(String backup_file_path) {
        File backupFile = new File(backup_file_path);
        if (backupFile.exists()) {
            return true;
        }
        return false;
    }

    public Response generarBackup() throws IOException {
        if (this.existePath(databaseConfig.getBACKUP_FILE_PATH())) {
            String nameFile = this.cambioDeFormatoArchivo();
            String backupCommand = databaseConfig.getMYSQL_PATH() + "\\mysqldump" + " --user=" + databaseConfig.getUSERNAME() +
                    " --password=" + databaseConfig.getPASSWORD() + " --databases " + databaseConfig.getDB_NAME()
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
                return new Response("Copia de seguridad correctamente guardada en " + filePath);
            } else {
                return new Response("Error: El archivo no existe");
            }
        }
        return new Response(String.format("El path %s no existe", databaseConfig.getBACKUP_FILE_PATH()));
    }
}
