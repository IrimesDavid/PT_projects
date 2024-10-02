package org.example.Model;
import java.util.Random;

public class Client implements Comparable<Client>{
    private int id;
    private int tArrival;
    private int tService;

    public Client(int id, int mintService, int maxtService, int mintArrival, int maxtArrival){
        Random random = new Random();
        this.id = id;
        tArrival = random.nextInt(mintArrival,maxtArrival + 1);
        tService = random.nextInt(mintService, maxtService + 1 );
    }

    public int getId() {
        return id;
    }
    public int gettArrival() {
        return tArrival;
    }
    public int gettService() {
        return tService;
    }
    public void decrementtService(){
        tService--;
    }
    @Override
    public String toString() {
        return "(" + id + ", " + tArrival + ", " + tService + ")";
    }
    @Override
    public int compareTo(Client o) {
        return Integer.compare(this.tArrival, o.tArrival);
    }
}
