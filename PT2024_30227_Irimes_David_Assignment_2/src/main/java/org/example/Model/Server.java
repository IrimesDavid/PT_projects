package org.example.Model;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod = new AtomicInteger();
    private AtomicInteger nbOfClients = new AtomicInteger();
    public Server(){
        waitingPeriod.set(0);
        clients = new LinkedBlockingQueue<>();
        nbOfClients.set(0);
    }
    public void addClient(Client newClient){
       clients.add(newClient);
       waitingPeriod.addAndGet(newClient.gettService());
       nbOfClients.incrementAndGet();
    }
    @Override
    public void run() {
        while (true) {
            try {
                Client client = clients.peek();
                if(client != null) {
                    if(client.gettService() == 1){
                        Thread.sleep(1000);
                        clients.poll();
                        waitingPeriod.addAndGet(-1);
                        nbOfClients.decrementAndGet();
                    }
                    else {
                        Thread.sleep(1000);
                        client.decrementtService();
                        waitingPeriod.addAndGet(-1);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public AtomicInteger getNbOfClients() {
        return nbOfClients;
    }

    public BlockingQueue<Client> getClients(){
        return  clients;
    }
    @Override
    public String toString() {
        if(clients.isEmpty()) return "empty";
        return clients + ";";
    }
}
