package com.callcenter.app.abstracts;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jaime
 * @Clase encargada de representar una llamada
 */
public abstract class Call implements Runnable {
    //duracion minima de una llamada en milisegundos
    public final static int MIN_DURATION = 5_000;
    //duracion minima de una llamada en milisegundos
    public final static int MAX_DURATION = 10_000;
    //ticket que representa el numero de caso al que esta asociado la llamada
    protected long ticketNumber;

    public long getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(long ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    /**
     * @return tiempo de duracion de la llamada
     */
    public int getDuration() {
        return ThreadLocalRandom
                .current()
                .nextInt(MIN_DURATION, MAX_DURATION + 1);
    }

}
