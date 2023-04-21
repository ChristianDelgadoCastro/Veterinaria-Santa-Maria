/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

import com.at.vsm.model.Mascota;
import com.at.vsm.model.Cliente;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author david
 */
public class PruebaMascotas {
    public static void main (String [] args){
        
        ControllerMascotas cm = new ControllerMascotas();
        
        //insertar mascotas
        Cliente c = new Cliente();
        c.setIdCliente(1);
        
        Mascota m = new Mascota();
        m.setFotografia("");
        m.setNombre("Gasolino");
        m.setEspecie("Gato");
        m.setRaza("Egipcio");
        m.setGenero("Macho");
        m.setEdad("4 meses");
        m.setPeso(2);
        m.setDetalles("Anda drogado");
        
        m.setCliente(c);
        
        try {
            cm.update(m);
            System.out.println("Mascota insertada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar la cita: " + e.getMessage());
        }
        
        //actualizar mascotas
        // Creamos un objeto Mascota con los datos que queremos actualizar
        Mascota mascota = new Mascota();
        mascota.setIdMascota(1);
        mascota.setCollar("1234");
        mascota.setFotografia("foto.jpg");
        mascota.setNombre("Fido");
        mascota.setEspecie("Perro");
        mascota.setRaza("Bulldog");
        mascota.setGenero("Macho");
        mascota.setEdad("5 años");
        mascota.setPeso(20.5);
        mascota.setDetalles("Es muy juguetón");
        
        // Creamos un objeto Cliente con el id correspondiente a la mascota
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        mascota.setCliente(cliente);
        
        try {
            // Invocamos al método update con la mascota creada
            cm.update(mascota);
            System.out.println("La mascota se actualizó correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar la mascota: " + e.getMessage());
        }
        
    }
}
