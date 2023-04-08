-- VISTAS DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023
-- Fecha de correción: 02/04/2023

USE veterinaria;

-- Creamos la vista para los clientes activos
DROP VIEW IF EXISTS v_clientes_activos;
CREATE VIEW v_clientes_activos AS(
SELECT c.idCliente, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, 
	   u.*, numeroUnico, estatus FROM persona p
INNER JOIN cliente c ON p.idPersona = c.idPersona
INNER JOIN usuario u ON c.idUsuario = u.idUsuario
WHERE estatus = 1);

-- Creamos la vista para los clientes inactivos
DROP VIEW IF EXISTS v_clientes_inactivos;
CREATE VIEW v_clientes_inactivos AS(
SELECT c.idCliente, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, 
	   u.*, numeroUnico, estatus FROM persona p
INNER JOIN cliente c ON p.idPersona = c.idPersona
INNER JOIN usuario u ON c.idUsuario = u.idUsuario
WHERE estatus = 0);

-- Creamos la vista para todos los clientes
DROP VIEW IF EXISTS v_clientes;
CREATE VIEW v_clientes AS(
SELECT c.idCliente, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email, 
	   u.*, numeroUnico, estatus FROM persona p
INNER JOIN cliente c ON p.idPersona = c.idPersona
INNER JOIN usuario u ON c.idUsuario = u.idUsuario);

-- Creamos la vista para los empleados activos
DROP VIEW IF EXISTS v_empleados_activos;
CREATE VIEW v_empleados_activos AS(
SELECT e.idEmpleado, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email,
	   u.*, numeroUnico, puesto, estatus FROM persona p
INNER JOIN empleado e ON p.idPersona = e.idPersona
INNER JOIN usuario u ON e.idUsuario = u.idUsuario
WHERE estatus = 1);

-- Creamos la vista para los empleados inactivos
DROP VIEW IF EXISTS v_empleados_inactivos;
CREATE VIEW v_empleados_inactivos AS(
SELECT e.idEmpleado, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email,
	   u.*, numeroUnico, puesto, estatus FROM persona p
INNER JOIN empleado e ON p.idPersona = e.idPersona
INNER JOIN usuario u ON e.idUsuario = u.idUsuario
WHERE estatus = 0);

-- Creamos la vista para todos los empleados
DROP VIEW IF EXISTS v_empleados;
CREATE VIEW v_empleados AS(
SELECT e.idEmpleado, p.idPersona, nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc, email,
	   u.*, numeroUnico, puesto, estatus FROM persona p
INNER JOIN empleado e ON p.idPersona = e.idPersona
INNER JOIN usuario u ON e.idUsuario = u.idUsuario);

-- Creamos la vista para todas las citas
DROP VIEW IF EXISTS v_citas;
CREATE VIEW v_citas AS (
SELECT c.idCita, c.fechaCita, c.horaCita, c.estatus AS citaEstatus,
       cl.idCliente, cl.numeroUnico AS clienteNumeroUnico, cl.estatus AS clienteEstatus,
       pc.idPersona AS idPersonaCliente, pc.nombre AS clienteNombre, pc.apellidoP AS clienteApellidoP, 
       pc.apellidoM AS clienteApellidoM, pc.genero AS clienteGenero, pc.domicilio AS clienteDomicilio,
       pc.telefono AS clienteTelefono, pc.rfc AS clienteRFC, pc.email AS clienteEmail,
       e.idEmpleado, e.numeroUnico AS empleadoNumeroUnico, e.puesto AS empleadoPuesto, e.estatus AS empleadoEstatus,
       pe.idPersona AS idPersonaEmpleado, pe.nombre AS empleadoNombre, pe.apellidoP AS empleadoApellidoP,
       pe.apellidoM AS empleadoApellidoM, pe.genero AS empleadoGenero, pe.domicilio AS empleadoDomicilio,
       pe.telefono AS empleadoTelefono, pe.rfc AS empleadoRFC, pe.email AS empleadoEmail
FROM cita c
INNER JOIN cliente cl ON c.idCliente = cl.idCliente
INNER JOIN empleado e ON c.idEmpleado = e.idEmpleado
INNER JOIN persona pc ON cl.idPersona = pc.idPersona
INNER JOIN persona pe ON e.idPersona = pe.idPersona
ORDER BY idCita
);

-- Creamos la vista para las citas agendadas
DROP VIEW IF EXISTS v_citas_activas;
CREATE VIEW v_citas_activas AS (
SELECT c.idCita, c.fechaCita, c.horaCita, c.estatus AS citaEstatus,
       cl.idCliente, cl.numeroUnico AS clienteNumeroUnico, cl.estatus AS clienteEstatus,
       pc.idPersona AS idPersonaCliente, pc.nombre AS clienteNombre, pc.apellidoP AS clienteApellidoP, 
       pc.apellidoM AS clienteApellidoM, pc.genero AS clienteGenero, pc.domicilio AS clienteDomicilio,
       pc.telefono AS clienteTelefono, pc.rfc AS clienteRFC, pc.email AS clienteEmail,
       e.idEmpleado, e.numeroUnico AS empleadoNumeroUnico, e.puesto AS empleadoPuesto, e.estatus AS empleadoEstatus,
       pe.idPersona AS idPersonaEmpleado, pe.nombre AS empleadoNombre, pe.apellidoP AS empleadoApellidoP,
       pe.apellidoM AS empleadoApellidoM, pe.genero AS empleadoGenero, pe.domicilio AS empleadoDomicilio,
       pe.telefono AS empleadoTelefono, pe.rfc AS empleadoRFC, pe.email AS empleadoEmail
FROM cita c
INNER JOIN cliente cl ON c.idCliente = cl.idCliente
INNER JOIN empleado e ON c.idEmpleado = e.idEmpleado
INNER JOIN persona pc ON cl.idPersona = pc.idPersona
INNER JOIN persona pe ON e.idPersona = pe.idPersona
WHERE c.estatus = 1
ORDER BY idCita);

-- Creamos la vista para las citas canceladas o atendidas
DROP VIEW IF EXISTS v_citas_inactivas;
CREATE VIEW v_citas_inactivas AS (
SELECT c.idCita, c.fechaCita, c.horaCita, c.estatus AS citaEstatus,
       cl.idCliente, cl.numeroUnico AS clienteNumeroUnico, cl.estatus AS clienteEstatus,
       pc.idPersona AS idPersonaCliente, pc.nombre AS clienteNombre, pc.apellidoP AS clienteApellidoP, 
       pc.apellidoM AS clienteApellidoM, pc.genero AS clienteGenero, pc.domicilio AS clienteDomicilio,
       pc.telefono AS clienteTelefono, pc.rfc AS clienteRFC, pc.email AS clienteEmail,
       e.idEmpleado, e.numeroUnico AS empleadoNumeroUnico, e.puesto AS empleadoPuesto, e.estatus AS empleadoEstatus,
       pe.idPersona AS idPersonaEmpleado, pe.nombre AS empleadoNombre, pe.apellidoP AS empleadoApellidoP,
       pe.apellidoM AS empleadoApellidoM, pe.genero AS empleadoGenero, pe.domicilio AS empleadoDomicilio,
       pe.telefono AS empleadoTelefono, pe.rfc AS empleadoRFC, pe.email AS empleadoEmail
FROM cita c
INNER JOIN cliente cl ON c.idCliente = cl.idCliente
INNER JOIN empleado e ON c.idEmpleado = e.idEmpleado
INNER JOIN persona pc ON cl.idPersona = pc.idPersona
INNER JOIN persona pe ON e.idPersona = pe.idPersona
WHERE c.estatus = 0
ORDER BY idCita);