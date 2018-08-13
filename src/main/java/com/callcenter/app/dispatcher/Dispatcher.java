package com.callcenter.app.dispatcher;

import com.callcenter.app.abstracts.Employee;
import com.callcenter.app.entities.AnswerMachine;
import com.callcenter.app.entities.Client;
import com.callcenter.app.entities.Directive;
import com.callcenter.app.entities.Operator;
import com.callcenter.app.entities.Supervisor;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 *
 * @author Jaime
 */
public class Dispatcher {

    private boolean executing;
    //objeto que permite crear un pool de hilos y asignacion de tareas
    private ExecutorService service;
    //cola de clientes (FIFO) optimizada para el uso compartido en hilos
    private ConcurrentLinkedQueue<Client> queueClients;
    //boqueo explicito que garantiza el correcto acceso al recurso compartido
    private ReentrantLock lock;
    //lista de empleados de la empresa
    private List<Employee> employees;

    public static int attended = 0;

    public Dispatcher(List<Employee> employees) {
        /*permite procesar todas las tareas con un pool de 12 hilos
        *en caso de presentarse una nueva tarea quedara en cola
        *esperando a ser atendido mas adelante
         */
        service = Executors.newFixedThreadPool(12);
        this.queueClients = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
        this.executing = false;
        /*garantiza la sincronizacion de los metodos en caso que se 
         *necesite modificar la lista desde varios hilos
         */
        this.employees = Collections.synchronizedList(employees);
    }

    /**
     * Obtiene las solicitudes de los clientes y los pone en cola de espera
     *
     * Este metodo permite adicionar un cliente a la cola, despues inicia el
     * proceso en caso de que se encuentre detenido
     *
     * @param client Cliente que solicita atención
     */
    public void dispatchCall(Client client) {
        //se adiciona a la cola un nuevo cliente que requiere se atendido 
        queueClients.offer(client);
        
        if (!executing) {
            init();
        }
    }

    /**
     * Este metodo permite realizar todo el proceso de atencion de un caso,
     * involucrando: el cliente, la llamada y el empleado encargado de atender
     * el caso.
     *
     * Adicionalmente, permite administra los estados y mensajes entre los
     * objetos mencionados anteriormente
     */
    private void init() {
        //bloque el acceso al recurso por otros hilos
        lock.lock();
        try {
            // verifica que la cola no este vacia 
            while (!queueClients.isEmpty()) {
                Client client = queueClients.poll();

                //obtiene la lista de los empleados que se encuentran disponibles
                List<Employee> avaibles
                        = Collections.synchronizedList(employees.stream()
                                .filter(e -> e.isAvailable())
                                .collect(Collectors.toList()));

                //se obtiene el empleado que puede atender al cliente dependiendo
                //el nivel gerarquico 
                Employee empleadoAsignado
                        = avaibles.stream()
                                .filter((e) -> e instanceof Operator)
                                .findFirst()
                                .orElse(avaibles.stream()
                                        .filter((e) -> e instanceof Supervisor)
                                        .findFirst()
                                        .orElse(avaibles.stream()
                                                .filter((e) -> e instanceof Directive)
                                                .findFirst()
                                                .orElse(new AnswerMachine(queueClients))))
                                                .setAvailable(false);

                service.execute(empleadoAsignado.attendCall(client));
            }
        } finally {
            // Se finaliza la ejecucion del proceso
            this.executing = false;
            lock.unlock();
        }
    }

    public ExecutorService getService() {
        return service;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public static int getAttended() {
        return attended;
    }
    
}
