USE veterinaria;

INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Carlos Yael", "Cordova Chavez", "1", "Metropolitano #174","4775382196","CARLO081101T45");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Cristian Ulises", "Ortega Padron", "1", "Aurora Boreal #119","4791493165","OEPC022602D56");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Alvaro", "Ortega Padron", "1", "Aurora Boreal #119","47725365936","ALFG071107G65");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Alvaro", "Ortega Martinez", "1", "Aurora Boreal #119","4772861889","ADFT657298D54");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Maria Imelda", "Padron Campos", "M", "Aurora Boreal #119","4777051610","PACI764390T54");

INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Rosario", "Hernadez Sanchez", "F", "Marquez #43","4775387529","RSHSF64820H321");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Ismael", "Padron Esquivel", "M", "Box #43","4776372986","IPEM3892A54GTE");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Nestor", "Torres Hernandez", "M", "Torre Molino #12", "4771276453", "NTHM658302ART");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Carlos Leonel", "Carranco Sanchez", "M", "Miramar #23", "4772367409", "CLCSMM64839A52");
INSERT INTO persona(nombre, apellidos, genero, domicilio, telefono, rfc) VALUES("Yahir Esquivel", "Flores Verlasquez", "M", "Mar de China #33", "4774538265", "TEFVM23675AEE");


SELECT * FROM persona;

INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("voladorMamut","Jugger","Administrador");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("cris","cris2518","Empleado");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("arlv","e1234","");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("arlv2","e21234","Empleado");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("imeldaR3","imelda1234","Empleado");

INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("Ros", "rosario123", "Cliente");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("Ismael", "ismael1234", "Cliente");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("Nestor", "nez@123", "Cliente");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("CLeonel", "leo1234", "Cliente");
INSERT INTO usuario(nombreUsuario, contrasenia, rol) VALUES("EsquiYahir", "yahir1234", "Cliente");

SELECT * FROM usuario;

INSERT INTO empleado(numeroEmpleado, puesto, estatus, idUsuario, idPersona) VALUES("ECARLO7462", "Administrador", 1, 1, 1);
INSERT INTO empleado(numeroEmpleado, puesto, estatus, idUsuario, idPersona) VALUES("EOEPC26384", "Cirujano", 1, 2, 2);
INSERT INTO empleado(numeroEmpleado, puesto, estatus, idUsuario, idPersona) VALUES("EALFG76352", "Limpieza", 1, 3, 3);
INSERT INTO empleado(numeroEmpleado, puesto, estatus, idUsuario, idPersona) VALUES("ADFT646382", "Cirujano", 1, 4, 4);
INSERT INTO empleado(numeroEmpleado, puesto, estatus, idUsuario, idPersona) VALUES("PACI755281", "Recepcionista", 1, 5, 5);

SELECT * FROM empleado;

INSERT INTO cliente(correo, numeroUnico, estatus, idPersona, idUsuario) VALUES("Rosario@gmail.com", "ROSARIO2367492", 1, 6, 6);
INSERT INTO cliente(correo, numeroUnico, estatus, idPersona, idUsuario) VALUES("Ismael@gmail.com", "ISMAEL231856AE", 1, 7, 7);
INSERT INTO cliente(correo, numeroUnico, estatus, idPersona, idUsuario) VALUES("Nestor@gmail.com", "NESTORH65832GF", 1, 8, 8);
INSERT INTO cliente(correo, numeroUnico, estatus, idPersona, idUsuario) VALUES("Leonel@gmail.com", "LEONEL547382TT", 1, 9, 9);
INSERT INTO cliente(correo, numeroUnico, estatus, idPersona, idUsuario) VALUES("Yahir@gmail.com", "YAHIRH456732RF", 1, 10, 10);


SELECT * FROM cliente;

INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Luki", "Pitbull", "2 años", 17.5, "Mediano de color cafe", 1, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Hachi", "Frespol", "5 años", 12.0, "Pequeño de color negro con blanco", 2, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Tomas", "Boxer", "8 años", 25.6, "Grande de color negro", 3, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Frida", "Pug", "3 años", 14.7, "Pequeño de color cafe", 4, 1);
INSERT INTO mascota(nombre, raza, edad, peso, descripcion, idCliente, estatus) VALUES("Lucas", "Gran danes", "6 años", 38.4, "Grande de color negro con manchas blancas", 5, 1);


SELECT * FROM mascota;
SELECT * FROM cliente;

INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2021-02-01', '12:54:43', 1, 1, 1);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-24', '01:34:55', 1, 2, 2);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-25', '06:43:00', 1, 3, 3);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-15', '07:23:00', 1, 4, 4);
INSERT INTO cita(fechaCita, horaCita, estatus, idCliente, idEmpleado) VALUES('2022-03-27', '05:00:00', 1, 5, 5);

SELECT * FROM cita WHERE estatus = 1;


UPDATE mascota SET nombre = "Frida", edad = "20 años", peso = 12.5,  descripcion = "Grande"  WHERE idMascota = 38;
-- "UPDATE mascota SET nombre = "Frida", edad = "20 años", peso = 12.5,  descripcion = "Grande"  WHERE idMascota = 38;