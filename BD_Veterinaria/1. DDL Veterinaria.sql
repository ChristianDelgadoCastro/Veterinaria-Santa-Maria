-- DDL DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023
-- Fecha de correción: 02/04/2023

DROP DATABASE IF EXISTS veterinaria;
CREATE DATABASE veterinaria;

USE veterinaria;

-- Listo  1
CREATE TABLE persona(
	idPersona INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL,
    apellidoP VARCHAR(64) NOT NULL,
    apellidoM varchar(64) not null,
    genero VARCHAR(2) NOT NULL DEFAULT 'O',
    domicilio VARCHAR(200) NOT NULL,
    telefono VARCHAR(25) NOT NULL,
    rfc VARCHAR(14) NOT NULL,
    email VARCHAR(200) NOT NULL
);

-- Listo 2
CREATE TABLE usuario(
	idUsuario INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreUsuario VARCHAR(48) NOT NULL,
    contrasenia VARCHAR(48) NOT NULL,
    rol VARCHAR(24) NOT NULL,
    fotografia LONGTEXT,
    token varchar(65) not null default '',
    dateLastToken       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP -- STR_TO_DATE('01/01/1901 00:00:00', '%d/%m/%Y %H:%i:%S')
);

-- Listo 3
CREATE TABLE cliente(
	idCliente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    numeroUnico VARCHAR(70) NOT NULL,
    estatus INT(11) NOT NULL DEFAULT 1,
    idPersona INT(11) NOT NULL,
    idUsuario INT(11) NOT NULL,
	CONSTRAINT  fk_cliente_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT  fk_cliente_usuario  FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idusuario) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 4
CREATE TABLE empleado(
	idEmpleado INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    numeroUnico VARCHAR(70) NOT NULL,
    puesto VARCHAR(20),
    estatus INT(11) NOT NULL DEFAULT 1,
    idPersona INT(11) NOT NULL,
    idUsuario INT(11) NOT NULL,
    CONSTRAINT  fk_empleado_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_empleado_usuario FOREIGN KEY (idUsuario)
				REFERENCES usuario(idUsuario) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 6
CREATE TABLE cita(
	idCita INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    fechaCita DATE NOT NULL,
    horaCita TIME NOT NULL,
    estatus INT(11) NOT NULL DEFAULT 1,
    idCliente INT(11),
    idEmpleado INT(11) NOT NULL,
    CONSTRAINT fk_cita_cliente FOREIGN KEY (idCliente)
				REFERENCES cliente(idCliente) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  fk_cita_empleado  FOREIGN KEY (idEmpleado) 
                REFERENCES empleado(idEmpleado) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 5
CREATE TABLE mascota(
	idMascota INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    numeroUnico VARCHAR(65),
    collar LONGTEXT,
    fotografia LONGTEXT,
    nombre VARCHAR(50) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(40) NOT NULL,
    genero VARCHAR(6) NOT NULL,
    edad VARCHAR(50) NOT NULL,
    peso DOUBLE NOT NULL,
    detalles VARCHAR(200) NOT NULL,
    estatus INT(11) NOT NULL DEFAULT 1,
    idCliente INT(11) NOT NULL,
    CONSTRAINT fk_mascota_cliente FOREIGN KEY (idCliente)
				REFERENCES cliente(idCliente) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 7
CREATE TABLE historialMedico(
	idHistorial	 INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idMascota INT(11),
    fecha DATE NOT NULL,
    diagnostico VARCHAR(200)  NOT NULL,
    CONSTRAINT fk_historialMedico_mascota FOREIGN KEY (idMascota)
				REFERENCES mascota(idMascota) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 8
CREATE TABLE detalle_Cita(
	idDetalle_Cita INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreTratamiento VARCHAR(150) NOT NULL,
    costoTratamiento FLOAT NOT NULL,
    nombreProducto VARCHAR(200) NOT NULL,
    idCita INT(11) NOT NULL,
    CONSTRAINT fk_detalleCita_cita FOREIGN KEY (idCita)
				REFERENCES cita(idCita) ON DELETE CASCADE ON UPDATE CASCADE
);
