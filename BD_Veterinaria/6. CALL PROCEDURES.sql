-- INSERTAR SECRETARIOS Y VETERINARIOS CON PROCEDIMIENTOS ALMACENADOS DE BASE DE DATOS VETERINARIA
-- AUTOR: David Alonso Floreano Parra
-- Fecha de codificación: 06/03/2023
-- Fecha de correción: 02/04/2023

USE veterinaria;

-- Primero a los veterinarios

CALL insertarEmpleado("Alejandro", "Rodriguez", "Garcia", "M", "Calle Emiliano Zapata s/n Col. Las Joyas, Leon, Guanajuato", "4795645255","RODA010597", "arodriguez@santamaria.com",
					  "alejandro", "veterinario", "", "Veterinario",
                      @out1, @out2, @out3, @out4);
                      
CALL insertarEmpleado("Marco Rafael", "Rangel", "Mora", "M", "20 de enero s/n Nuevo Valle de Moreno, Leon, Guanajuato", "4778556588","RAMM030902", "mgangel@santamaria.com",
					  "marco", "veterinario", "", "Veterinario",
                      @out1, @out2, @out3, @out4);
                      
CALL insertarEmpleado("Aurora Guadalupe", "Ortega", "Saucedo", "F", "Blvd Delta s/n Col. 10 de Mayo, Leon, Guanajuato", "4795587854","OESA031080", "aortega@santamaria.com",
					  "aurora", "veterinario", "", "Veterinario",
                      @out1, @out2, @out3, @out4);
                      
-- Ahora a los secretarios
CALL insertarEmpleado("Irais", "Parra", "Meza", "F", "Blvd Vicente Valtierra 7895 Col. El Mirador, Leon, Guanajuato", "4778596588", "PAMI211200", "iparra@santamaria.com",
					  "irais", "secretario", "", "Secretario", 
                      @out1, @out2, @out3, @out4);
                      
CALL insertarEmpleado("Ricardo", "Vazquez", "Floreano", "M", "Calle Bicentenario 8546 Col. Maravillas, Leon, Guanajuato", "4777856544", "VAFR041185", "rvazquez@santamaria.com",
					  "ricardo", "secretario", "", "Secretario", 
                      @out1, @out2, @out3, @out4);
