-- VISTAS DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023

-- Creamos la vista para los clientes activos
DROP VIEW IF EXISTS v_clientes_activos;
CREATE VIEW v_clientes_activos AS(
SELECT c.idCliente, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, fotografia,
	   u.*, numeroUnico, estatus FROM persona p
INNER JOIN cliente c ON p.idPersona = c.idPersona
INNER JOIN usuario u ON c.idUsuario = u.idUsuario
WHERE estatus = 1);

-- Creamos la vista para los clientes
DROP VIEW IF EXISTS v_clientes_inactivos;
CREATE VIEW v_clientes_inactivos AS(
SELECT c.idCliente, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, fotografia,
	   u.*, numeroUnico, estatus FROM persona p
INNER JOIN cliente c ON p.idPersona = c.idPersona
INNER JOIN usuario u ON c.idUsuario = u.idUsuario
WHERE estatus = 0);

-- Creamos la vista para los empleados activos
DROP VIEW IF EXISTS v_empleados_activos;
CREATE VIEW v_empleados_activos AS(
SELECT e.idEmpleado, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, fotografia,
	   u.*, numeroEmpleado, puesto, estatus FROM persona p
INNER JOIN empleado e ON p.idPersona = e.idPersona
INNER JOIN usuario u ON e.idUsuario = u.idUsuario
WHERE estatus = 1);

-- Creamos la vista para los empleados inactivos
DROP VIEW IF EXISTS v_empleados_inactivos;
CREATE VIEW v_empleados_inactivos AS(
SELECT e.idEmpleado, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, fotografia,
	   u.*, numeroEmpleado, puesto, estatus FROM persona p
INNER JOIN empleado e ON p.idPersona = e.idPersona
INNER JOIN usuario u ON e.idUsuario = u.idUsuario
WHERE estatus = 0);
