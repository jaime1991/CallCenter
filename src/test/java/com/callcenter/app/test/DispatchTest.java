/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.callcenter.app.test;

import com.callcenter.app.abstracts.Employee;
import com.callcenter.app.dispatcher.Dispatcher;
import com.callcenter.app.entities.Client;
import com.callcenter.app.entities.Directive;
import com.callcenter.app.entities.Operator;
import com.callcenter.app.entities.Supervisor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jaime
 */
public class DispatchTest {
    
    private List<Employee> employeesList;
    
    @Before
    public void init(){        
        employeesList = new ArrayList<>();
        
        IntStream.rangeClosed(1, 7).forEach(i -> {
            employeesList.add(new Operator("operator_" + i, "operator"+i+"@center"));
        });

        IntStream.rangeClosed(1, 2).forEach(i -> {
            employeesList.add(new Supervisor("supervisor_" + i, "supervisor"+i+"@center"));
        });

        IntStream.rangeClosed(1, 1).forEach(i -> {
            employeesList.add(new Directive("directive_" + i, "directive"+i+"@center"));
        });
    }
    
    @Test
    public void casosAtendidos(){
        Dispatcher dispatcher = new Dispatcher(employeesList);
        
        IntStream.rangeClosed(1, 15)
                .parallel()
                .forEach(i -> {
                    Client client = new Client("Client_" + i, "Client_" + i + "@client");
                    dispatcher.dispatchCall(client);
                });
        
        dispatcher.getService().shutdown();
        while (!dispatcher.getService().isTerminated()) {}
        
        assertEquals("15 llamadas realizadas, 15 llamadas atendidas ",15,Dispatcher.attended);
    }
}
