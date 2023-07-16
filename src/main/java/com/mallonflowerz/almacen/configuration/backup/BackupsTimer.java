package com.mallonflowerz.almacen.configuration.backup;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import com.mallonflowerz.almacen.configuration.backup.services.DatabaseBackupsService;
import com.mallonflowerz.almacen.productosYUsuarios.models.Response;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class BackupsTimer {

    private final DatabaseBackupsService databaseBackupsService;
    private final DatabaseConfig databaseConfig;

    @PostConstruct
    public void startTimer() {
        Timer timer = new Timer();

        int backupTime = databaseConfig.getTIME_BACKUP_HOURS();

        long interval = backupTime * 60 * 60 * 1000;

        // Crea una instancia de TimerTask
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    // Llama al método generarBackup para generar el archivo de respaldo
                    Response response = databaseBackupsService.generarBackup();
                    System.out.println(response.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Programa la tarea para que se ejecute repetidamente con el retraso y el
        // intervalo especificados
        timer.scheduleAtFixedRate(task, interval, interval);
    }

    public static void main(String[] args) {
        SpringApplication.run(BackupsTimer.class, args);
    }
}
