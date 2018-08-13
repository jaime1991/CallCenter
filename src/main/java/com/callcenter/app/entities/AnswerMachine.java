/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.app.entities;

import com.callcenter.app.abstracts.Employee;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Jaime
 * Clase encargada de atender las llamadas cuando no hay disponible
 * ningun empleado
 */
public class AnswerMachine extends Employee {

    private ConcurrentLinkedQueue<Client> queueClient;

    public AnswerMachine(ConcurrentLinkedQueue<Client> queueClient) {
        this.queueClient = queueClient;
        this.name = "Machine";
    }

    /**
     * Metodo que se encarga de encolar la solicitud del cliente 
     * hasta encontrar un empleado que pueda atender su llamada
     * @param client cliente que solicita un servicio
     * @return implementacion de la interfaz runnable
     */
    @Override
    public Runnable attendCall(Client client) {
        queueClient.add(client);
        return () -> {};
    }
}
