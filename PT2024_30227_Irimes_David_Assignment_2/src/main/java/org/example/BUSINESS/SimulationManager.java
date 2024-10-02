package org.example.BUSINESS;
import org.example.GUI.SimulationFrame;
import org.example.Model.Client;
import org.example.Model.Server;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;

import static org.example.BUSINESS.Scheduler.SelectionPolicy.SHORTEST_TIME;
import static org.example.BUSINESS.Scheduler.SelectionPolicy.SHORTEST_QUEUE;


public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public Scheduler.SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Client> generatedClients;
    private AtomicReference<Double> avgWaitingTime = new AtomicReference<>(0.0);
    private double avgServiceTime;
    private int[] peakHour = {0,0};
    private int peakCnt = 0;
    private BufferedWriter writer = null;
    private boolean retryStart = false;
    //------------------------------------------------------------------------------------------------------------------
    public SimulationManager(){
        do {
            frame = new SimulationFrame();
            frame.setVisible(true);
            System.out.println("Waiting to start...");
            try {
                frame.getLatch().await(); // wait until user presses 'start'
                retryStart = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting...");
                this.setTimeLimit(frame.getTimeLimit());
                this.setMaxProcessingTime(frame.getMaxProcessTime());
                this.setMinProcessingTime(frame.getMinProcessTime());
                this.setMaxArrivalTime(frame.getMaxArrivalTime());
                this.setMinArrivalTime(frame.getMinArrivalTime());
                this.setNumberOfServers(frame.getNbOfServers());
                this.setNumberOfClients(frame.getNbOfClients());
                this.setSelectionPolicy(frame.getSelectionPolicy());
                frame.setStartFlag(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                frame.dispose();
                frame.setStartFlag(false);
                retryStart = true;
            }
        }while (retryStart);
        //--------------------------------------------------------------------------------------------------------------
        //define maxNoServers & maxClientsPerServer here
        scheduler = new Scheduler(20,numberOfClients);
        if(Scheduler.getMaxNoServers() < numberOfServers){
            try {
                throw new Exception("EXCEPTION: number of servers too large");
            }catch (Exception e){
                System.out.println(e.getMessage());
                Thread.currentThread().stop();
                System.exit(0);
            }
        }else{
            for (int i = 0; i < numberOfServers; ++i) {
                Server s = new Server();
                scheduler.getServers().add(s);
                Thread serverThread = new Thread(s);
                serverThread.start();
            }
        }

        selectionPolicy = SHORTEST_TIME;
        scheduler.changeStrategy(selectionPolicy);

        generatedClients = new ArrayList<>();
        generateNRandomClients();

        //make a log file
        try {
            writer = new BufferedWriter(new FileWriter("log.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void generateNRandomClients(){
        int sum = 0;
        for(int i=0; i<numberOfClients; ++i){
            Client client = new Client(i,minProcessingTime,maxProcessingTime,minArrivalTime,maxArrivalTime);
            generatedClients.add(client);
            sum += client.gettService();
        }
        Collections.sort(generatedClients);
        //find average service time
        avgServiceTime = sum/(double)numberOfClients;
    }
    @Override
    public void run() {
        int currentTime = 0;
        while (currentTime < timeLimit){
            try {
                Iterator<Client> iterator = generatedClients.iterator();
                while (iterator.hasNext()) {
                    Client client = iterator.next();
                    if (client.gettArrival() == currentTime) {
                        scheduler.dispatchClient(client,avgWaitingTime);
                        iterator.remove(); // Remove client from list using iterator
                    }
                }
                //update peakHour
                int sumClients = 0;
                for(int i = 0; i < numberOfServers; ++i)
                    sumClients += scheduler.getServers().get(i).getNbOfClients().get();
                if(peakCnt < sumClients){
                    peakCnt = sumClients;
                    peakHour[0] = currentTime;
                    peakHour[1] = currentTime + 1;
                }
                //update UI frame & log file
                frame.setOutputArea(currentTime, generatedClients,scheduler,writer);
                //increment
                currentTime++;
                Thread.sleep(1000);
                if(generatedClients.isEmpty()) {
                    int notDone = 0;
                    for(Server q: scheduler.getServers()){
                        if(!q.getClients().isEmpty())
                            notDone = 1;
                    }
                    if(notDone == 0) {
                        frame.setOutputArea(currentTime, generatedClients,scheduler,writer);
                        break;
                    }
                }
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //calculate average waiting time
        Double avg = (avgWaitingTime.get()/(double)(numberOfServers))/(double)currentTime;
        //display the simulation results & in log file
        frame.displaySimResults(avg,avgServiceTime,peakHour);
        try {
            writer.write("Average waiting time: " + String.format("%.2f",avg) + "\n");
            writer.write("Average service time: " + String.format("%.2f",avgServiceTime) + "\n");
            writer.write("Peak hour: " + peakHour[0] + "-" + peakHour[1] + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread.currentThread().stop();
        System.out.println("Done");
    }
    //------------------------------------------------------------------------------------------------------------------
    public SimulationFrame getFrame() {
        return frame;
    }
    //------------------------------------------------------------------------------------------------------------------
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    public void setMaxProcessingTime(int maxProcessingTime) {
        this.maxProcessingTime = maxProcessingTime;
    }
    public void setMinProcessingTime(int minProcessingTime) {
        this.minProcessingTime = minProcessingTime;
    }
    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }
    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }
    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }
    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }
    public void setSelectionPolicy(String selectionPolicy) {
        if(selectionPolicy.equals("Time strategy")){
            this.selectionPolicy = SHORTEST_TIME;
        }
        else{
            this.selectionPolicy = SHORTEST_QUEUE;
        }
    }
}
