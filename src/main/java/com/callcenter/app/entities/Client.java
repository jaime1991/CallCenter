package com.callcenter.app.entities;

import com.callcenter.app.abstracts.Person;

/**
 * @author Jaime
 * Clase que representa el cliente que inicia un nuevo caso 
 * a traves de una llamada
 */
public class Client extends Person {

    //permite verificar si el cliente fue o no atendido
    private boolean attended = false;

    public Client(String name, String email) {
        super.name = name;
        super.email = email;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    @Override
    public String toString() {
        return "Client [ name " + name + ", email " + email 
                + " attended=" + attended + "]";
    }
}
