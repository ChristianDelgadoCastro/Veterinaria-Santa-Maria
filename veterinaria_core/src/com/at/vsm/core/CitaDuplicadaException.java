/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.at.vsm.core;

/**
 *
 * @author david
 */
public class CitaDuplicadaException extends RuntimeException {
    public CitaDuplicadaException() {
        super("Ya existe una cita agendada para esa fecha y hora.");
    }
}
