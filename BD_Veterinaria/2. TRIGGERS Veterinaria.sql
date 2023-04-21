-- TRIGGERS DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023
-- Fecha de correción: 02/04/2023

USE veterinaria;

-- Disparador para crear el numero unico para las mascotas

DROP TRIGGER IF EXISTS crearNumeroUnicoMascota;
DELIMITER $$
CREATE TRIGGER crearNumeroUnicoMascota 
BEFORE INSERT ON mascota
FOR EACH ROW
BEGIN
	DECLARE var_idMascota INT;	
	SELECT AUTO_INCREMENT INTO var_idMascota
	FROM information_schema.TABLES 
	WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'mascota';
	SET NEW.numeroUnico = CONCAT('M', var_idMascota, '-', NEW.especie, '-C', NEW.idCliente, '-CLV', round( rand()*100000));
END
$$
DELIMITER ;
