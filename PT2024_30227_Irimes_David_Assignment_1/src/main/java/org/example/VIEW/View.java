package org.example.VIEW;

import org.example.MODEL.Operation;
import org.example.MODEL.Polynomial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel polyName1;
    private JLabel polyName2;
    private JPanel operatorPanel;
    private JTextField outputText;
    private JLabel outputLabel;
    private JPanel outputPanel;
    private JButton addButton;
    private JButton substractButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private JButton integrateButton;
    private JButton deriveButton;
    private JButton clearButton;

    public View(Operation op){
        setTitle("PolyCalc");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550,400);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        outputText.setEditable(false);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Output");
                outputText.setText("");
                textField1.setText("");
                textField2.setText("");
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Add");
                String s1 = textField1.getText();
                String s2 = textField2.getText();
                s1 = Polynomial.adjustPolynomialString(s1);
                s2 = Polynomial.adjustPolynomialString(s2);
                if(s1 == "" || s2 == "") {
                    System.out.println("EXCEPTION: <NoInput>");
                    outputText.setText("");
                }
                else{
                    Polynomial op1 = new Polynomial(s1);
                    Polynomial op2 = new Polynomial(s2);
                    outputText.setText(Operation.addPoly(op1,op2));
                }
            }
        });
        substractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Substract");
                String s1 = textField1.getText();
                String s2 = textField2.getText();
                s1 = Polynomial.adjustPolynomialString(s1);
                s2 = Polynomial.adjustPolynomialString(s2);
                if(s1 == "" || s2 == "") {
                    System.out.println("EXCEPTION: <NoInput>");
                    outputText.setText("");
                }
                else{
                    Polynomial op1 = new Polynomial(s1);
                    Polynomial op2 = new Polynomial(s2);
                    outputText.setText(Operation.substractPoly(op1,op2));
                }
            }
        });
        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Multiply");
                String s1 = textField1.getText();
                String s2 = textField2.getText();
                s1 = Polynomial.adjustPolynomialString(s1);
                s2 = Polynomial.adjustPolynomialString(s2);
                if(s1 == "" || s2 == "") {
                    System.out.println("EXCEPTION: <NoInput>");
                    outputText.setText("");
                }
                else{
                    Polynomial op1 = new Polynomial(s1);
                    Polynomial op2 = new Polynomial(s2);
                    outputText.setText(Operation.multiplyPoly(op1,op2));
                }
            }
        });
        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Divide");
                String s1 = textField1.getText();
                String s2 = textField2.getText();
                s1 = Polynomial.adjustPolynomialString(s1);
                s2 = Polynomial.adjustPolynomialString(s2);
                if(s1 == "" || s2 == "") {
                    System.out.println("EXCEPTION: <NoInput>");
                    outputText.setText("");
                }
                else{
                    Polynomial op1 = new Polynomial(s1);
                    Polynomial op2 = new Polynomial(s2);
                    outputText.setText(Operation.dividePoly(op1,op2));
                }
            }
        });
        integrateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Integrate");
                String input = JOptionPane.showInputDialog(null,"Introduceti polinomul.");
                if(input != null && !input.isEmpty()) {
                    System.out.println("<Execute operation>");
                    input = Polynomial.adjustPolynomialString(input);
                    Polynomial poly = new Polynomial(input);
                    outputText.setText(Operation.integratePoly(poly));
                }else{
                    System.out.println("<Canceled>");
                }
            }
        });
        deriveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputLabel.setText("Derive");
                String input = JOptionPane.showInputDialog(null,"Introduceti polinomul.");
                if(input != null && !input.isEmpty()) {
                    System.out.println("<Execute operation>");
                    input = Polynomial.adjustPolynomialString(input);
                    Polynomial poly = new Polynomial(input);
                    outputText.setText(Operation.derivePoly(poly));
                }else{
                    System.out.println("<Canceled>");
                }
            }
        });
    }
}
