package org.example;

import org.example.BUSINESS.SimulationManager;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        while (true) {
            SimulationManager gen = new SimulationManager();
            Thread t = new Thread(gen);
            t.start();
                while(true){
                    if(gen.getFrame().getStartFlag()) {
                        gen.getFrame().setStartFlag(false);
                        break;
                    }
                }
                gen.getFrame().dispose();
                gen = null;
                t.stop();
                t = null;
                System.out.println("Simulation done");
        }
    }
}