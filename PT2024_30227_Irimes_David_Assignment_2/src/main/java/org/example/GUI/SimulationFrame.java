package org.example.GUI;

import org.example.BUSINESS.Scheduler;
import org.example.Model.Client;
import org.example.Model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SimulationFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel outputPanel;
    private JComboBox selectionPolicy;
    private JTextArea outputArea;
    private JButton startButton;
    private JSpinner timeLimit;
    private JSpinner maxProcessTime;
    private JSpinner minProcessTime;
    private JSpinner nbOfServers;
    private JSpinner nbOfClients;
    private JSpinner minArrivalTime;
    private JSpinner maxArrivalTime;
    private JLabel minArrivalTimeL;
    private JLabel maxArrivalTimeL;
    private boolean startFlag = false;
    private CountDownLatch latch = new CountDownLatch(1);
    //------------------------------------------------------------------------------------------------------------------
    public int getTimeLimit() throws Exception {
        int timeLimitValue = (Integer) timeLimit.getValue();
        if(timeLimitValue < 0) {
            throw new Exception("EXCEPTION: cannot have negative input");
        }
        return timeLimitValue;
    }
    public int getMaxProcessTime()throws Exception {
        if((int)maxProcessTime.getValue() < 0)
            throw new Exception("EXCEPTION: cannot have negative input");
        if((int)maxProcessTime.getValue() < (int)minProcessTime.getValue())
            throw new Exception("EXCEPTION: cannot have max smaller than min");
        return (Integer) maxProcessTime.getValue();
    }
    public int getMinProcessTime() throws Exception {
        if((int)minProcessTime.getValue() < 0)
            throw new Exception("EXCEPTION: cannot have negative input");
        return (Integer) minProcessTime.getValue();
    }
    public int getMaxArrivalTime()throws Exception {
        if((int)maxArrivalTime.getValue() < 0)
            throw new Exception("EXCEPTION: cannot have negative input");
        if((int)maxArrivalTime.getValue() < (int)minArrivalTime.getValue())
            throw new Exception("EXCEPTION: cannot have max smaller than min");
        return (Integer) maxArrivalTime.getValue();
    }
    public int getMinArrivalTime() throws Exception {
        if((int)minArrivalTime.getValue() < 0)
            throw new Exception("EXCEPTION: cannot have negative input");
        return (Integer) minArrivalTime.getValue();
    }
    public int getNbOfServers() throws Exception {
        int nbOfServersValue = (Integer) nbOfServers.getValue();
        if(nbOfServersValue < 0) {
            throw new Exception("EXCEPTION: cannot have negative input");
        }
        return nbOfServersValue;
    }

    public int getNbOfClients() throws Exception {
        int nbOfClientsValue = (Integer) nbOfClients.getValue();
        if(nbOfClientsValue < 0) {
            throw new Exception("EXCEPTION: cannot have negative input");
        }
        return nbOfClientsValue;
    }
    public String getSelectionPolicy() {
        return String.valueOf(selectionPolicy);
    }
    public CountDownLatch getLatch() {
        return latch;
    }
    public boolean getStartFlag() {
        return startFlag;
    }
    //------------------------------------------------------------------------------------------------------------------
    public void setOutputArea(int currentTime, List<Client> generatedClients, Scheduler scheduler, BufferedWriter writer){
        outputArea.setText("Time: " + currentTime + "\n");
        outputArea.append("Waiting clients: " + generatedClients + "\n");

        List<Server> servers = scheduler.getServers();
        int i = 0;
        for (Server server : servers) {
            ++i;
            outputArea.append("Q" + i + " :: " + server + "\n");
        }
        try{
            writer.write(outputArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void displaySimResults(double avgWaitingTime, double avgServiceTime, int[] peakHour){
        outputArea.append("\n" + "Average waiting time: " + String.format("%.2f",avgWaitingTime) + "\n");
        outputArea.append("Average service time: " + String.format("%.2f",avgServiceTime) + "\n");
        outputArea.append("Peak hour: " + peakHour[0] + "-" + peakHour[1] + "\n");
    }
    public void setStartFlag(boolean startFlag) {
        this.startFlag = startFlag;
    }
    //------------------------------------------------------------------------------------------------------------------
    public SimulationFrame(){
        setTitle("QueueSim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550,400);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        inputPanel.setPreferredSize(new Dimension(135,50));
        selectionPolicy.addItem("Time strategy");
        selectionPolicy.addItem("Shortest queue strategy");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                latch.countDown();
                setStartFlag(true);
            }
        });

    }
}
