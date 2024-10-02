package org.example.BUSINESS;

import org.example.Model.Client;
import org.example.Model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addClient(List<Server> servers, Client client, AtomicReference<Double> avgWaitingTime)throws Exception {
        AtomicInteger minNbOfClients = new AtomicInteger();
        Server minQueue = new Server();
        minNbOfClients.set(Integer.MAX_VALUE);

        for (Server queue : servers) {
            if(queue.getNbOfClients().get() < minNbOfClients.get()) {
                minNbOfClients.set(queue.getNbOfClients().get());
                minQueue = queue;
            }
        }
        if(minQueue.getNbOfClients().get() >= Scheduler.getMaxClientsPerServer()) {
            throw new Exception("EXCEPTION: <no more space in queue>");
        }
        else{
            minQueue.addClient(client);
            Double avg = (double)minQueue.getWaitingPeriod().get();
            avgWaitingTime.getAndAccumulate(avg, Double::sum);
        }
    }
}
