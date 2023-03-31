insertarEmpleado DELIMITER $$
DROP procedure IF exists insertarEmpleado;
CREATE PROCEDURE insertarEmpleado(	/* Datos Personales */
                                    IN	var_nombre          VARCHAR(64),    -- 1
                                    IN  var_apellidoP		VARCHAR(64),    -- 2
                                    IN  var_apellidoM        VARCHAR(64),    -- 3
                                    IN  var_genero          VARCHAR(2),     -- 4
                                    IN	var_domicilio       VARCHAR(200),   -- 5
                                    IN	var_telefono        VARCHAR(25),    -- 6
                                    IN	var_rfc             VARCHAR(14),    -- 7
                                    IN  var_email			VARCHAR(200), 	-- 8
                                    IN 	var_fotografia		LONGTEXT,   	-- 9
                                    
                                    /* Datos de Usuario */
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 10
                                    IN	var_contrasenia     VARCHAR(48),    -- 11
                                    IN	var_rol             VARCHAR(24),    -- 12
                                    
                                    /* Datos de Empleado */
                                    IN	var_puesto          VARCHAR(35),    -- 13
                                    
                                    /* Valores de Retorno */
                                    OUT	var_idUsuario       INT,            -- 14
                                    OUT	var_idPersona       INT,            -- 15
                                    OUT	var_idEmpleado      INT,            -- 16
                                    OUT var_numeroEmpleado  VARCHAR(64)     -- 17
				)                                    
    BEGIN        
        -- Comenzamos insertando los datos de la Persona:
        INSERT INTO persona (nombre, apellidoP, apellidoM, genero, domicilio, telefono, rfc,email, fotografia)
                    VALUES( var_nombre, var_apellidoP, var_apellidoM, var_genero, var_domicilio, var_telefono, var_rfc, var_email, var_fotografia);
        -- Obtenemos el ID de Persona que se generó:
        SET var_idPersona = LAST_INSERT_ID();

        -- Insertamos los datos de seguridad del Empleado:
        INSERT INTO usuario ( nombreUsuario, contrasenia, rol) 
                    VALUES( var_nombreUsuario, var_contrasenia, var_rol);
        -- Obtenemos el ID de Usuario que se generó:
        SET var_idUsuario = LAST_INSERT_ID();

        --  Generamos el numero de empleado.
        --  Comenzamos agregando el primer digito (la letra E):
        SET var_numeroEmpleado = 'E';        
        --  Agregamos los digitos del RFC si los tiene:
        IF  LENGTH(var_rfc) >= 4 THEN
            SET var_numeroEmpleado = CONCAT(var_numeroEmpleado, SUBSTRING(var_rfc, 1, 4));
        END IF;
        --  Finalmente, agregamos el timestamp:
        SET var_numeroEmpleado = CONCAT(var_numeroEmpleado, CAST(UNIX_TIMESTAMP() AS CHAR));

        -- Finalmente, insertamos en la tabla Empleado:
        INSERT INTO empleado ( numeroEmpleado, puesto, estatus, idUsuario, idPersona) 
                    VALUES( var_numeroEmpleado, var_puesto, 1, var_idUsuario, var_idPersona);
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
                                    IN 	var_fotografia		LONGTEXT,		-- 9
                                    
                                    /* Datos de Usuario */
                                    IN	var_nombreUsuario   VARCHAR(48),    -- 10
                                    IN	var_contrasenia     VARCHAR(48),    -- 11
                                    IN	var_rol             VARCHAR(24),    -- 12
                                    
                                    /* Datos de Empleado */
                                    IN  var_puesto          VARCHAR(20),    -- 13
                                    --  El nÃºmero de empleado no se considera
                                    --  porque no es actualizable.
                                    
                                    /* ID's de las tablas relacionadas con el Empleado */
                                    IN	var_idUsuario       INT,            -- 14
                                    IN	var_idPersona       INT,            -- 15
                                    IN	var_idEmpleado      INT             -- 16
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
                                email = var_email,
                                fotografia = var_fotografia
                        WHERE   idPersona = var_idPersona;

        -- Actualizamos los datos de Seguridad:
        UPDATE usuario  SET     nombreUsuario = var_nombreUsuario,
                                contrasenia = var_contrasenia,
                                rol = var_rol
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
                                    IN var_fotografia		LONGTEXT,		-- 9
                                    
                                    /* Datos de Usuario */
                                    IN  var_nombreUsuario   VARCHAR(48),	-- 10
                                    IN  var_contrasenia     VARCHAR(48),	-- 11
                                    IN  var_rol             VARCHAR(24),	-- 12
                                                                        
                                    /* Valores de Retorno */
                                    OUT var_idUsuario       INT,			-- 13
                                    OUT var_idPersona       INT,			-- 14				
                                    OUT var_idCliente       INT,			-- 15
                                    OUT var_numeroUnico		VARCHAR(70)		-- 16
                                )
    BEGIN
        -- Comenzamos insertando en la tabla Persona:
        INSERT INTO persona (nombre, apellidoP, apellidoM, genero,
                              domicilio, telefono, rfc, email, fotografia)
                    VALUES(var_nombre, var_apellidoP, var_apellidoM, 
                            var_genero, var_domicilio, var_telefono, var_rfc, var_email, var_fotografia);
        -- Recuperamos el ID de la Persona que se genero:
        SET var_idPersona = LAST_INSERT_ID();

        -- Insertamos en la tabla de Usuario con los datos de seguridad:
        INSERT INTO usuario (nombreUsuario, contrasenia, rol) 
                    VALUES( var_nombreUsuario, var_contrasenia, var_rol);
        -- Recuperamos el ID del Usuario que se generÃÂ³ de forma automÃÂ¡tica:
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
                                        IN var_fotografia		LONGTEXT,		-- 9

                                        /* Datos de Usuario */
                                        IN  var_nombreUsuario   VARCHAR(48),	-- 10
										IN  var_contrasenia     VARCHAR(48),	-- 11
                                        
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
                                contrasenia = var_contrasenia
                        WHERE   idUsuario = var_idUsuario;

		-- En la tabla cliente no actualizamos nada, ya que solo está el número único de cliente
    END
$$
DELIMITER ;