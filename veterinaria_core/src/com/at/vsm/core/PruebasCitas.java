package com.at.vsm.core;

import com.at.vsm.model.Cita;
import com.at.vsm.model.Cliente;
import com.at.vsm.model.Empleado;

import java.sql.SQLException;
import java.util.List;

public class PruebasCitas {

    public static void main(String[] args) {

        ControllerCitas controllerCitas = new ControllerCitas();

        // Insertar cita
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1);

        Cita cita = new Cita();
        cita.setFechaCita("2023-04-07");
        cita.setHoraCita("15:30:00");
        cita.setCliente(cliente);
        cita.setEmpleado(empleado);

        try {
            controllerCitas.insert(cita);
            System.out.println("Cita insertada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar la cita: " + e.getMessage());
        }

        // Obtener todas las citas
        try {
            List<Cita> citas = controllerCitas.getAll("");
            System.out.println("Todas las citas:");
            for (Cita c : citas) {
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener todas las citas: " + e.getMessage());
        }

        // Obtener citas activas
        try {
            List<Cita> citas = controllerCitas.getAllActives("");
            System.out.println("Citas activas:");
            for (Cita c : citas) {
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener citas activas: " + e.getMessage());
        }

        // Obtener citas inactivas
        try {
            List<Cita> citas = controllerCitas.getAllInactives("");
            System.out.println("Citas inactivas:");
            for (Cita c : citas) {
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener citas inactivas: " + e.getMessage());
        }
        
        // Obtener citas buscadas
        try {
            List<Cita> citas = controllerCitas.search("Rosario");
            System.out.println("Citas buscadas:");
            for (Cita c : citas) {
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener citas inactivas: " + e.getMessage());
        }
    }
}
