package org.example;

import org.example.MODEL.Operation;
import org.example.VIEW.View;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Operation op = new Operation();
        View view = new View(op);

        view.setVisible(true);
    }
}
