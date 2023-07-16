package com.mallonflowerz.almacen.configuration.backup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import lombok.Data;

@Component
@Data
public class DatabaseConfig {

    @Value("${almacen.time_backup_hours}")
    private Integer TIME_BACKUP_HOURS;

    @Value("${almacen.backup_file_path}")
    private String BACKUP_FILE_PATH;

    @Value("${almacen.username}")
    private String USERNAME;

    @Value("${almacen.password}")
    private String PASSWORD;

    @Value("${almacen.db_name}")
    private String DB_NAME;

    @Value("${almacen.mysql_path}")
    private String MYSQL_PATH;

}
