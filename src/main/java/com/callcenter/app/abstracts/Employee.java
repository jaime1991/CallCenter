package com.callcenter.app.abstracts;

import com.callcenter.app.entities.Case;
import com.callcenter.app.entities.Client;

/**
 * @author Jaime
 * Clase base que identifica a todos los empleados que trabajan en la entidad
 */
public abstract class Employee extends Person {

    //perimite verificar si esta disponible o no para atender un nuevo caso
    private boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public Employee setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    /**
     * Metodo que permite simular la atencion de un caso a traves de una llamada
     * @param client cliente que requiere atencion 
     * @return la llamada ya atendida por parte del empleados
     */
    public Runnable attendCall(Client client) {
        Call call = new Case(this, client);
        return call;
    }
    
    @Override
    public String toString() {
        return "Empleado [Instance class=" + this.getClass().getSimpleName() +
                " " + name + ", avaible =" + available + "]";
    }
}
