package org.example.BUSINESS;

import org.example.Model.Client;
import org.example.Model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface Strategy {
    public void addClient(List<Server> servers, Client client, AtomicReference<Double> avgWaitingTime)throws Exception;
}
