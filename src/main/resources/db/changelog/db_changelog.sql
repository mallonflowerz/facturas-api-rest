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

CREATE TABLE DetalleFactura (
  id BINARY(36) PRIMARY KEY,
  nombre_producto VARCHAR(100) NOT NULL,
  valorVenta DECIMAL(30, 2) NOT NULL,
  cantidad DECIMAL(30, 2) NOT NULL,
  valorTotal DECIMAL(30, 2) NOT NULL
);

CREATE TABLE Tercero (
  id BINARY(36) PRIMARY KEY,
  nit VARCHAR(20) NOT NULL,
  razon_social VARCHAR(100) NOT NULL,
  primer_nombre VARCHAR(30) NOT NULL,
  segundo_nombre VARCHAR(30) NOT NULL,
  primer_apellido VARCHAR(30) NOT NULL,
  segundo_apellido VARCHAR(30) NOT NULL,
  direccion VARCHAR(30) NOT NULL,
  ciudad_residencia VARCHAR(30) NOT NULL,
  pais_residencia VARCHAR(30) NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  ultima_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Factura (
  id BINARY(36) PRIMARY KEY,
  tercero_id BINARY(36),
  fechaDefactura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (tercero_id) REFERENCES Tercero(id)
);

CREATE TABLE DetalleFactura_Factura (
id BINARY(36) PRIMARY KEY,
factura_id BINARY(36),
detallefactura_id BINARY(36),
FOREIGN KEY (factura_id) REFERENCES Factura(id),
FOREIGN KEY (detallefactura_id) REFERENCES DetalleFactura(id)
);