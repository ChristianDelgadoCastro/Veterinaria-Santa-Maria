-- PROCEDIMIENTOS ALMACENADOS DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023
-- Fecha de correción: 02/04/2023

USE veterinaria;

DROP procedure IF exists insertarEmpleado;
DELIMITER $$
CREATE PROCEDURE insertarEmpleado(	/* Datos Personales */
                                    IN	var_nombre          VARCHAR(64),    -- 1
                                    IN  var_apellidoP		VARCHAR(64),    -- 2
                                    IN  var_apellidoM        VARCHAR(64),   -- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN	var_domicilio       VARCHAR(200),   -- 5
                                    IN	var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    IN  var_email			VARCHAR(200), 	-- 8
                                    
                                    /* Datos de Usuario */
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 9
                                    IN	var_contrasenia     VARCHAR(48),    -- 10
									IN 	var_fotografia		LONGTEXT,   	-- 11
                                    
                                    /* Datos de Empleado */
                                    IN	var_puesto          VARCHAR(35),    -- 12
                                    
                                    /* Valores de Retorno */
                                    OUT	var_idUsuario       INT,            -- 13
                                    OUT	var_idPersona       INT,            -- 14
                                    OUT	var_idEmpleado      INT,            -- 15
                                    OUT var_numeroUnico  VARCHAR(64)     	-- 16
				)                                    
    BEGIN        
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc,email)
                    VALUES( var_nombre, var_apellidoP, var_apellidoM, var_genero, var_domicilio, var_telefono, var_rfc, var_email);
        -- Obtenemos el ID de Persona que se generó:
        SET var_idPersona = LAST_INSERT_ID();

        -- Insertamos los datos de seguridad del Empleado:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol, fotografia) 
                    VALUES( var_nombreUsuario, var_contrasenia, "Empleado", var_fotografia);
        -- Obtenemos el ID de Usuario que se generó:
        SET var_idUsuario = LAST_INSERT_ID();

        --  Generamos el numero de empleado.
        --  Comenzamos agregando el primer digito (la letra E):
        SET var_numeroUnico = 'E';        
        --  Agregamos los digitos del RFC si los tiene:
        IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroUnico = CONCAT(var_numeroUnico, SUBSTRING(var_rfc, 1, 4));
        END IF;
        --  Finalmente, agregamos el timestamp:
        SET var_numeroUnico = CONCAT(var_numeroUnico, CAST(UNIX_TIMESTAMP() AS CHAR));

        -- Finalmente, insertamos en la tabla Empleado:
        INSERT INTO empleado ( numeroUnico, puesto, idUsuario, idPersona) 
                    VALUES( var_numeroUnico, var_puesto, var_idUsuario, var_idPersona);
        -- Obtenemos el ID del Empleado que se generÃ³:
        SET var_idEmpleado = LAST_INSERT_ID();
    END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarEmpleado;
DELIMITER $$
CREATE PROCEDURE actualizarEmpleado(	/* Datos Personales */
                                    IN  var_nombre          VARCHAR(64),    -- 1
                                    IN 	var_apellidoP		VARCHAR(64),	-- 2
                                    IN  var_apellidoM		VARCHAR(64),	-- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN  var_domicilio       VARCHAR(200),   -- 5
                                    IN  var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    IN  var_email           VARCHAR(200),	-- 8
                                    
                                    /* Datos de Usuario */
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 9
                                    IN	var_contrasenia     VARCHAR(48),    -- 10
                                    IN 	var_fotografia		LONGTEXT,		-- 11
                                    
                                    /* Datos de Empleado */
                                    IN  var_puesto          VARCHAR(20),    -- 12
                                    --  El número de empleado no se considera
                                    --  porque no es actualizable.
                                    
                                    /* ID's de las tablas relacionadas con el Empleado */
                                    IN	var_idUsuario       INT,            -- 13
                                    IN	var_idPersona       INT,            -- 14
                                    IN	var_idEmpleado      INT             -- 15
                                )                                    
    BEGIN
        -- Comenzamos actualizando los datos personales del Empleado:
        UPDATE persona  SET     nombre = var_nombre, 
                                apellidoP = var_apellidoP,
                                apellidoM = var_apellidoM,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono, 
                                rfc = var_rfc,
                                email = var_email
                        WHERE   idPersona = var_idPersona;

        -- Actualizamos los datos de Seguridad:
        UPDATE usuario  SET     nombreUsuario = var_nombreUsuario,
                                contrasenia = var_contrasenia,
                                fotografia = var_fotografia
                        WHERE   idUsuario = var_idUsuario;

        -- Actualizamos sus datos de Empleado:
        UPDATE empleado SET     puesto = var_puesto 
                                -- estatus = var_estatus,
                        WHERE   idEmpleado = var_idEmpleado;        
    END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS insertarCliente;
DELIMITER $$
CREATE PROCEDURE insertarCliente(   /* Datos Personales */
                                    IN  var_nombre          VARCHAR(64),	-- 1
                                    IN 	var_apellidoP		VARCHAR(64),	-- 2
                                    IN  var_apellidoM		VARCHAR(64),	-- 3
                                    IN  var_genero          VARCHAR(2),		-- 4
                                    IN  var_domicilio       VARCHAR(200),	-- 5
                                    IN	var_telefono        VARCHAR(25),	-- 6
                                    IN	var_rfc             VARCHAR(14),	-- 7
                                    IN  var_email			VARCHAR(200), 	-- 8
                                    
                                    /* Datos de Usuario */
                                    IN  var_nombreUsuario   VARCHAR(48),	-- 9
                                    IN  var_contrasenia     VARCHAR(48),	-- 10
                                    IN 	var_fotografia		LONGTEXT,		-- 11
                                                                        
                                    /* Valores de Retorno */
                                    OUT var_idUsuario       INT,			-- 12
                                    OUT var_idPersona       INT,			-- 13				
                                    OUT var_idCliente       INT,			-- 14
                                    OUT var_numeroUnico		VARCHAR(70)		-- 15
                                )
    BEGIN
        -- Comenzamos insertando en la tabla Persona:
        INSERT INTO persona (nombre, apellidoP, apellidoM, genero,
                              domicilio, telefono, rfc, email)
                    VALUES(var_nombre, var_apellidoP, var_apellidoM, 
                            var_genero, var_domicilio, var_telefono, var_rfc, var_email);
        -- Recuperamos el ID de la Persona que se genero:
        SET var_idPersona = LAST_INSERT_ID();

        -- Insertamos en la tabla de Usuario con los datos de seguridad:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol, fotografia) 
                    VALUES( var_nombreUsuario, var_contrasenia, "Cliente", var_fotografia);
        -- Recuperamos el ID del Usuario que se generó de forma automática:
        SET var_idUsuario = LAST_INSERT_ID();

--  Generamos el numero de empleado.
        --  Comenzamos agregando el primer digito (la letra E):
        SET var_numeroUnico = 'C';        
        --  Agregamos los digitos del RFC si los tiene:
        IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroUnico = CONCAT(var_numeroUnico, SUBSTRING(var_rfc, 1, 4));
        END IF;
        --  Finalmente, agregamos el timestamp:
        SET var_numeroUnico = CONCAT(var_numeroUnico, CAST(UNIX_TIMESTAMP() AS CHAR));

        -- Finalmente, insertamos en la tabla cliente:
        INSERT INTO cliente (numeroUnico, idUsuario, idPersona) 
                    VALUES(var_numeroUnico, var_idUsuario, var_idPersona);
        -- Recuperamos el ID del CLiente que se generÃÂ³ de forma automÃÂ¡tica:
        SET var_idCliente = LAST_INSERT_ID();
    END
$$
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarCliente;
DELIMITER $$
CREATE PROCEDURE actualizarCliente(     /* Datos Personales */
                                        IN  var_nombre          VARCHAR(64),	-- 1
                                        IN	var_apellidoP		VARCHAR(64),	-- 2
                                        IN  var_apellidoM		VARCHAR(64),	-- 3
                                        IN  var_genero          VARCHAR(2),		-- 4
                                        IN  var_domicilio       VARCHAR(200),	-- 5
                                        IN  var_telefono        VARCHAR(25),	-- 6
                                        IN  var_rfc             VARCHAR(14),	-- 7
                                        IN  var_email			VARCHAR(200),   -- 8

                                        /* Datos de Usuario */
                                        IN  var_nombreUsuario   VARCHAR(48),	-- 9
										IN  var_contrasenia     VARCHAR(48),	-- 10
										IN 	var_fotografia		LONGTEXT,		-- 11
                                        
                                        /* ID's de las tablas afectadas */
                                        IN var_idPersona        INT,			-- 12
                                        IN var_idUsuario        INT				-- 13
                                    )
    BEGIN
        -- Comenzamos actualizando los datos personales:
        UPDATE  persona SET     nombre = var_nombre, 
                                apellidoP = var_apellidoP,
                                apellidoM = var_apellidoM,
                                genero = var_genero,
                                domicilio = var_domicilio,
                                telefono = var_telefono, 
                                rfc = var_rfc,
                                email = var_email
                        WHERE   idPersona = var_idPersona;

        -- Finalmente, actualizamos los datos de seguridad:
        UPDATE  usuario SET     nombreUsuario = var_nombreUsuario, 
                                contrasenia = var_contrasenia,
                                fotografia = var_fotografia
                        WHERE   idUsuario = var_idUsuario;

		-- En la tabla cliente no actualizamos nada, ya que solo está el número único de cliente
    END
$$
DELIMITER ;

-- BUSCAR CLIENTES
DROP PROCEDURE IF exists buscarClientes;
DELIMITER $$
CREATE procedure buscarClientes(in datoBusqueda varchar(100))
begin
	select * from v_clientes
	where (
    numeroUnico like concat('%', datoBusqueda, '%') or
    rfc like concat('%', datoBusqueda, '%') or
    genero like concat('%', datoBusqueda, '%') or
    nombre like concat('%', datoBusqueda, '%') or
    apellidoP like concat('%', datoBusqueda, '%') or
    apellidoM like concat('%', datoBusqueda, '%') or
    domicilio like concat('%', datoBusqueda, '%') or
    telefono like concat('%', datoBusqueda, '%') or
    email like concat('%', datoBusqueda, '%'));
end
$$
delimiter ;

-- BUSCAR CLIENTES
DROP PROCEDURE IF exists buscarEmpleados;
DELIMITER $$
CREATE procedure buscarEmpleados(in datoBusqueda varchar(100))
begin
	select * from v_empleados
	where (
    numeroUnico like concat('%', datoBusqueda, '%') or
    rfc like concat('%', datoBusqueda, '%') or
    genero like concat('%', datoBusqueda, '%') or
    nombre like concat('%', datoBusqueda, '%') or
    apellidoP like concat('%', datoBusqueda, '%') or
    apellidoM like concat('%', datoBusqueda, '%') or
    domicilio like concat('%', datoBusqueda, '%') or
    telefono like concat('%', datoBusqueda, '%') or
    email like concat('%', datoBusqueda, '%') or
    puesto like concat('%', datoBusqueda, '%'));
end
$$
delimiter ;

-- BUSCAR CITAS
DROP PROCEDURE IF exists buscarCitas;
DELIMITER $$
CREATE procedure buscarCitas(in datoBusqueda varchar(100))
begin
	select * from v_citas
	where (
    fechaCita like concat('%', datoBusqueda, '%') or
    horaCita like concat('%', datoBusqueda, '%') or
    clienteNumeroUnico like concat('%', datoBusqueda, '%') or
    clienteNombre like concat('%', datoBusqueda, '%') or
    clienteApellidoP like concat('%', datoBusqueda, '%') or
    clienteApellidoM like concat('%', datoBusqueda, '%') or
    clienteGenero like concat('%', datoBusqueda, '%') or
    clienteDomicilio like concat('%', datoBusqueda, '%') or
    clienteTelefono like concat('%', datoBusqueda, '%') or
    clienteRFC like concat('%', datoBusqueda, '%') OR
    clienteEmail like concat('%', datoBusqueda, '%') OR
    empleadoNumeroUnico like concat('%', datoBusqueda, '%') OR
    empleadoPuesto like concat('%', datoBusqueda, '%') OR
    empleadoNombre like concat('%', datoBusqueda, '%') OR 
    empleadoApellidoP like concat('%', datoBusqueda, '%') OR 
    empleadoApellidoM like concat('%', datoBusqueda, '%') OR 
    empleadoDomicilio like concat('%', datoBusqueda, '%') OR 
    empleadoTelefono like concat('%', datoBusqueda, '%') OR 
    empleadoRFC like concat('%', datoBusqueda, '%') OR 
    empleadoEmail like concat('%', datoBusqueda, '%'));
end
$$
delimiter ;