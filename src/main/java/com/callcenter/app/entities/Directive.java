package com.callcenter.app.entities;

import com.callcenter.app.abstracts.Employee;

/**
 * @author Jaime
 * Representa uno de los 3 tipos de empleados que pueden atender los casos
 * de los clientes
 */
public class Directive extends Employee {

    public Directive(String name, String email) {
        super.name = name;
        super.email = email;
    }

}
