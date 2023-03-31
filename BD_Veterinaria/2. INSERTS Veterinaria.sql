
-- CLIENTE PARA PRUEBAS

-- insersión en la tabla PERSONA
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("Miguel", "Aceves", "Mejia", "H", "Calle de la esperanza #256 La Libertad, León, Guanajuato", "4778958654", "AEMM020204HGT", "miguel@gmail.com");
 
-- insersión en la tabla USUARIO
INSERT INTO usuario(nombreUsuario, contrasenia, rol)
VALUES ("miguelv", "1234", "Cliente");

-- insersión en la tabla CLIENTE
INSERT INTO cliente(numeroUnico, idPersona, idUsuario)
VALUES ("MIGUEL2367492", 1, 1);

-- EMPLEADO PARA PRUEBAS
-- Insersión en la tabla PERSONA
INSERT INTO persona(nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email)
VALUES ("David Alonso", "Floreano", "Parra", "H", "Aquiles Serdan #302 Nuevo Valle de Moreno, Leon, Gto", "4772889882", "FOPD020204HGT", "lonches@gmail.com");

select * from usuario;
select * from persona;

-- insersión en la tabla USUARIO
INSERT INTO usuario(nombreUsuario, contrasenia, rol)
VALUES ("lonches422", "1234", "Administrador");

-- insersión en la tabla EMPLEADO
INSERT INTO empleado(numeroEmpleado, puesto, idPersona, idUsuario)
VALUES ("LONCHES478594", "Administrador", 2, 2);

INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Luki", "Pitbull", "2 años", 17.5, "Mediano de color cafe", 1, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Hachi", "Frespol", "5 años", 12.0, "Pequeño de color negro con blanco", 2, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Tomas", "Boxer", "8 años", 25.6, "Grande de color negro", 3, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Frida", "Pug", "3 años", 14.7, "Pequeño de color cafe", 4, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Lucas", "Gran danes", "6 años", 38.4, "Grande de color negro con manchas blancas", 5, 1);

INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2021-02-01', '12:54:43', 1, 1, 1);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-24', '01:34:55', 1, 2, 2);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-25', '06:43:00', 1, 3, 3);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-15', '07:23:00', 1, 4, 4);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-27', '05:00:00', 1, 5, 5);
