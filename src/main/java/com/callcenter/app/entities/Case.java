/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.app.entities;

import com.callcenter.app.abstracts.Call;
import com.callcenter.app.abstracts.Employee;
import com.callcenter.app.dispatcher.Dispatcher;

/**
 * @author Jaime
 * Clase que representa la atencion de una llamada, en la cual se encuentra
 * el cliente junto con el correspondiente empleado que atendera la llamada 
 */
public class Case extends Call {

    private Employee employee;
    private Client client;
    //se obtiene la duracion de la llamada
    private int duration = getDuration();

    public Case(Employee employee, Client client) {
        this.employee = employee;
        this.client = client;
        this.ticketNumber = System.currentTimeMillis();
    }

    /**
     * Metodo encargado de procesar la llamada deteniendo el hilo 
     * el tiempo que se demoro la atencion del cliente
     */
    public void processCall() {
        try {
            System.out.println("Init Case " + employee + " client: " 
                    + client.getEmail());
            Thread.sleep(duration);
            System.out.println("Finish Case " + employee + " client: " 
                    + client.getEmail() + " duration: " + duration);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que sobreescrito en el que se define la intefaz runable
     * se encarga de procesar la llamada y cambiar los estados de los 
     * objetos que intervienen (cliente y empleado)
     */
    @Override
    public void run() {
        processCall();
        client.setAttended(true);
        employee.setAvailable(true);
        Dispatcher.attended++;
    }

    @Override
    public String toString() {
        return "Call [ employee =" + employee + ", client " + client
                + ", timeCall=" + duration + "]";
    }
}
