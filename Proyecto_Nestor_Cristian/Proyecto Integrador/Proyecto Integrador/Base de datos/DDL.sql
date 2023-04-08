CREATE DATABASE veterinaria;

USE veterinaria;

DROP DATABASE veterinaria;

-- Listo 3
CREATE TABLE cliente(
	idCliente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    correo VARCHAR(200) NOT NULL,
    numeroUnico VARCHAR(70) NOT NULL,
    estatus INT(11) NOT NULL DEFAULT 1,
    idPersona INT(11) NOT NULL,
    idUsuario INT(11) NOT NULL,
	CONSTRAINT  fk_cliente_persona  FOREIGN KEY (idPersona) 
                REFERENCES persona(idPersona) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT  fk_cliente_usuario  FOREIGN KEY (idUsuario) 
                REFERENCES usuario(idusuario) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo  1
CREATE TABLE persona(
	idPersona INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(64) NOT NULL,
    apellidos VARCHAR(64) NOT NULL,
    genero VARCHAR(2) NOT NULL DEFAULT 'O',
    domicilio VARCHAR(200) NOT NULL,
    telefono VARCHAR(25) NOT NULL,
    rfc VARCHAR(14) NOT NULL
);

-- Listo 4
CREATE TABLE empleado(
	idEmpleado INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    numeroEmpleado VARCHAR(64),
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
    estatus INT(11),
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
    nombre VARCHAR(50),
    raza VARCHAR(40),
    edad VARCHAR(50),
    peso FLOAT,
    descripcion VARCHAR(250),
    idCliente INT(11) NOT NULL,
    CONSTRAINT fk_mascota_cliente FOREIGN KEY (idCliente)
				REFERENCES cliente(idCliente) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 2
CREATE TABLE usuario(
	idUsuario INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreUsuario VARCHAR(48) NOT NULL,
    contrasenia VARCHAR(48) NOT NULL,
    rol VARCHAR(24) NOT NULL
);

-- Listo 7
CREATE TABLE historialMedico(
	idHistorial	 INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idMascota INT(11),
    CONSTRAINT fk_historialMedico_mascota FOREIGN KEY (idMascota)
				REFERENCES mascota(idMascota) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Listo 8
CREATE TABLE detalle_Cita(
	idDetalle_Cita INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nombreServicio VARCHAR(150) NOT NULL,
    nombreTratamiento VARCHAR(150) NOT NULL,
    costoTratamiento FLOAT NOT NULL,
    nombreProducto VARCHAR(200) NOT NULL,
    idCita INT(11) NOT NULL,
    CONSTRAINT fk_detalleCita_cita FOREIGN KEY (idCita)
				REFERENCES cita(idCita) ON DELETE CASCADE ON UPDATE CASCADE
);

USE veterinaria;

SELECT * FROM v_clientes;