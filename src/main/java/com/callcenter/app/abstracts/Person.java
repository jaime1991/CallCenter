package com.callcenter.app.abstracts;

/**
 * @author Jaime
 * Clase base que representa la informacion basica de cualquier 
 * persona que usa el aplicativo
 */
public abstract class Person {
    protected String name;
    protected String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
}
