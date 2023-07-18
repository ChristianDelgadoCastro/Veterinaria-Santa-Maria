-- DATOS DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023
-- Fecha de correción: 02/04/2023

USE veterinaria;

-- EMPLEADOS PARA PRUEBAS

-- Insersiones en la tabla PERSONA
-- PERSONA 1

INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Miguel", "Aceves", "Mejia", "M", "Calle de la esperanza #256 La Libertad, León, Guanajuato", "4778958654", "AEMM020204HGT", "miguel@santamaria.com");
-- PERSONA 2
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("David Alonso", "Floreano", "Parra", "M", "Aquiles Serdan #302 Nuevo Valle de Moreno, León, Guanajuato", "4772889882", "FOPD020204", "lonches@santamaria.com");
-- PERSONA 3
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Christian Emmanuel", "Delgado", "Castro", "M", "Emiliano Zapata s/n Col. San Carmen, San Francisco, Guanajuato", "4765432101", "DECC050406", "christian@santamaria.com");
-- PERSONA 4
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Ximena Alejandra", "Sanchez", "Nicasio", "F", "Blvd. Timoteo Lozano #5101 Col. La Libertad, León, Guanajuato", "4798956325", "SANX020203", "ximena@santamaria.com");
-- PERSONA 5
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Veronica Irene", "Alvarado", "Ruiz", "F", "Calle Linda s/n Col. Las Hilamas, Leon, Guanajuato", "4775454522", "AARV050198", "veronica@santamaria.com");

-- Inserciones en la tabla USUARIO
-- USUARIO 1
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("miguelaceves", "admin", "Empleado", "");
-- USUARIO 2
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("lonches", "admin", "Empleado", "");
-- USUARIO 3
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("christian", "admin", "Empleado", "");
-- USUARIO 4
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("ximena", "admin", "Empleado", "");
-- USUARIO 5
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("veronica", "admin", "Empleado", "");

-- Insersiones en la tabla Empleados
-- EMPLEADO 1
INSERT INTO empleado(numeroUnico, puesto, idPersona, idUsuario)
VALUES ("AEMM47859687", "Administrador", 1, 1);
-- EMPLEADO 2
INSERT INTO empleado(numeroUnico, puesto, idPersona, idUsuario)
VALUES ("FOPD0145789", "Administrador", 2, 2);
-- EMPLEADO 3
INSERT INTO empleado(numeroUnico, puesto, idPersona, idUsuario)
VALUES ("DECC7985647", "Administrador", 3, 3);
-- EMPLEADO 4
INSERT INTO empleado(numeroUnico, puesto, idPersona, idUsuario)
VALUES ("SANX7854689","Veterinario", 4, 4);
-- EMPLEADO 5
INSERT INTO empleado(numeroUnico, puesto, idPersona, idUsuario)
VALUES ("AARV785468", "Secretario", 5, 5);

-- CLIENTES PARA PRUEBAS

-- Insersiones en la tabla PERSONA
-- PERSONA 6
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Rey Adonaí", "Floreano", "Parra", "M", "Aquiles Serdan #302 Nuevo Valle de Moreno, León, Guanajuato", "4772567810", "FOPE051107", "adonai@gmail.com");
-- PERSONA 7
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Jesus Emmanuel", "Floreano", "Parra", "M", "Aquiles Serdan #302 Nuevo Valle de Moreno, León, Guanajuato", "4772547821", "FOPJ281099", "emmanuel@gmail.com");
-- PERSONA 8
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Isabel", "Delgado", "Castro", "F", "Emiliano Zapata s/n Col. San Carmen, San Francisco, Guanajuato", "4768556558", "DECI020300", "isabel@gmail.com");
-- PERSONA 9
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Marisela", "Parra", "Ortega", "F", "Aquiles Serdán #302 Nuevo Valle de Moreno, León, Guanajuato", "4779064578", "PAOM060677", "marisela@gmail.com");
-- PERSONA 10
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Bernardo de Jesús", "Floreano", "Garcia", "M", "Aquiles Serdán #302 Nuevo Valle de Moreno, León, Guanajuato", "4771273618", "FOGB200867", "bernardo@gmail.com");

-- Insersiones en la tabla USUARIO
-- USUARIO 6
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("adonai", "1234", "Cliente", "");
-- USUARIO 7
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("chuy", "1234", "Cliente", "");
-- USUARIO 8
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("chabelita", "1234", "Cliente", "");
-- USUARIO 9
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("marisela_parra", "1234", "Cliente", "");
-- USUARIO 10
INSERT INTO usuario(nombreUsuario, contrasenia, rol, fotografia)
VALUES ("bernardo", "1234", "Cliente", "");

-- Insersiones en la tabla CLIENTE
-- CLIENTE 1
INSERT INTO cliente(numeroUnico, idPersona, idUsuario)
VALUES ("FOPE74859867", 6, 6);
-- CLIENTE 2
INSERT INTO cliente(numeroUnico, idPersona, idUsuario)
VALUES ("FOPJ78456985", 7, 7);
-- CLIENTE 3
INSERT INTO cliente(numeroUnico, idPersona, idUsuario)
VALUES ("DECI48795674", 8, 8);
-- CLIENTE 4
INSERT INTO cliente(numeroUnico, idPersona, idUsuario)
VALUES ("PAOM47856987", 9, 9);
-- CLIENTE 5
INSERT INTO cliente(numeroUnico, idPersona, idUsuario)
VALUES ("FOGB45789754", 10, 10);

-- UNAS MASCOTITAS PARA PRUEBAS

INSERT INTO mascota (collar, nombre, especie, raza, genero, edad, peso, detalles, idCliente) 
VALUES ('d7b0e94e', 'Sanson', 'Perro', 'Pug', 'Macho', '4 meses', '5', 'Se está peleando con el diablo', '1');
INSERT INTO mascota (collar, nombre, especie, raza, genero, edad, peso, detalles, idCliente) 
VALUES ('9a474fbf', 'Pichirilo', 'Perro', 'Pitbull', 'Macho', '4 años', '30', 'Tiene una pata chueca', '2');
INSERT INTO mascota (nombre, especie, raza, genero, edad, peso, detalles, idCliente) 
VALUES ('Porqueria', 'Gato', 'Balines', 'Macho', '2 años', '2', 'No quiere comer desde hace 2 dias', '3');
INSERT INTO mascota (nombre, especie, raza, genero, edad, peso, detalles, idCliente) 
VALUES ('Milanesa', 'Perro', 'Chihuahua', 'Hembra', '1 año', '1', 'Anda bien bravo desde hace 3 dias', '4');
INSERT INTO mascota (nombre, especie, raza, genero, edad, peso, detalles, idCliente) 
VALUES ('Michi', 'Gato', 'Asiatico', 'Hembra', '1 año', '2', 'Se le metio el diablo jajajajaja', '5');

