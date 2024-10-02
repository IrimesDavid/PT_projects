package org.example.BUSINESS;

import org.example.Model.Client;
import org.example.Model.Server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Scheduler {
    private List<Server> servers;
    private static int maxNoServers; //defined by the developer, not the user
    private static int maxClientsPerServer; // -||-
    private Strategy strategy;

    public Scheduler(int inMaxNoServers, int inMaxClientsPerServer){
        servers = new CopyOnWriteArrayList<>();
        maxNoServers = inMaxNoServers;
        maxClientsPerServer = inMaxClientsPerServer;
    }
    public static int getMaxNoServers() {
        return maxNoServers;
    }
    public static int getMaxClientsPerServer() {
        return maxClientsPerServer;
    }
    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new TimeStrategy();
        }
    }
    public void dispatchClient(Client client, AtomicReference<Double> avgWaitingTime){
        try {
            strategy.addClient(servers, client, avgWaitingTime);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public List<Server> getServers(){
        return servers;
    }
    public enum SelectionPolicy{
        SHORTEST_QUEUE, SHORTEST_TIME
    }
}
