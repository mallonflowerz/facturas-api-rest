-- liquibase/db.changelog-v1.0.sql

-- Cambio de base de datos para crear la tabla 'productos'
-- Fecha de creación: 2023-07-05

-- Crear la tabla 'productos'
CREATE TABLE productos (
  id BINARY(36) PRIMARY KEY,
  codigo VARCHAR(20) NOT NULL,
  nombre_producto VARCHAR(100) NOT NULL,
  descripcion VARCHAR(500) NOT NULL,
  cantidad DECIMAL(30, 2) NOT NULL,
  valor_compra DECIMAL(30, 2) NOT NULL,
  valor_venta DECIMAL(30, 2) NOT NULL,
  disponible BOOLEAN NOT NULL,
  fecha_creacion TIMESTAMP NOT NULL,
  fecha_modificacion TIMESTAMP NOT NULL
);

-- Crear índice único en la columna 'codigo'
CREATE UNIQUE INDEX productos_uq ON productos (codigo);

-- Crear la tabla "Usuarios"
CREATE TABLE usuarios (
    id BINARY(36) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(25) NOT NULL,
    telefono VARCHAR(16) NOT NULL,
    email VARCHAR(60) NOT NULL,
    password VARCHAR(120) NOT NULL,
    enabled BOOLEAN DEFAULT true,
    activo BOOLEAN DEFAULT false,
    rol VARCHAR(155) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultima_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear indice unico para el email
CREATE INDEX usuario_uq ON usuarios (email);

CREATE TABLE historial_logins (
  id BINARY(36) PRIMARY KEY,
  version BIGINT NOT NULL,
  email VARCHAR(60) NOT NULL,
  activo BOOLEAN DEFAULT FALSE,
  fecha_inicio_sesion TIMESTAMP,
  fecha_cierre_sesion TIMESTAMP
);

CREATE UNIQUE INDEX user_uq ON historial_logins (email);
