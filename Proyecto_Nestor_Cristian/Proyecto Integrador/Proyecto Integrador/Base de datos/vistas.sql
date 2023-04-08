USE veterinaria;

CREATE VIEW v_clientes AS
    SELECT  P.*,
            C.idCliente,
            C.correo,
            C.numeroUnico,
            C.estatus,
            U.*
    FROM    persona P
            INNER JOIN cliente C ON C.idPersona = P.idPersona
            INNER JOIN usuario U ON U.idUsuario = C.idUsuario;
            
SELECT * FROM v_clientes;

CREATE VIEW v_empleados AS
    SELECT  P.*,
            E.idEmpleado,
            E.numeroEmpleado,
            E.puesto,
            E.estatus,
            U.*
    FROM    persona P
            INNER JOIN empleado E ON E.idPersona = P.idPersona
            INNER JOIN usuario U ON U.idUsuario = E.idUsuario;
            
SELECT * FROM v_empleados;