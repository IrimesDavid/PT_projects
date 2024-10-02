package org.example.MODEL;

import java.util.*;
import java.util.regex.*;

public class Polynomial {
    protected Map <Integer, Double> monomials = new HashMap<>();

    public static String adjustPolynomialString(String poly) {
        StringBuilder adjustedPoly = new StringBuilder();
        String[] terms = poly.split("(?=[-+])"); // Split după semnul + sau -
        for (String term : terms) {
            if (!term.isEmpty()) {
                if (term.contains("x") && !term.contains("^")) {
                    term = term.replace("x", "x^1");
                } else if (!term.contains("x"))  term += "x^0";
                adjustedPoly.append(term.replaceAll("\\s", ""));
            }
        }
        return adjustedPoly.toString();
    }
    public Polynomial(){

    }
    public Polynomial(String poly){
        String regex = "([-+]?\\d*\\.?\\d*)?x\\^?(\\d+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(poly);

        while (matcher.find()) {
            String coefficientStr = matcher.group(1);
            if(coefficientStr.equals("+") || coefficientStr.equals("-")) coefficientStr+="1";   //daca nu apare niciun coeficient
            String exponentStr = matcher.group(2);

            Double coefficient = null;
            if (coefficientStr != null && !coefficientStr.isEmpty()) {
                coefficient = Double.parseDouble(coefficientStr);
            } else {
               coefficient = 1.0;
            }
            Integer exponent = null;
            if (exponentStr != null && !exponentStr.isEmpty()) {
                exponent = Integer.parseInt(exponentStr);
            }

            monomials.put(exponent, coefficient);
        }
    }
    public void printPoly() {
        for (HashMap.Entry<Integer, Double> entry : monomials.entrySet()) {
            if(entry.getValue() - Math.floor(entry.getValue()) < 0.1 && entry.getValue()>0)
                System.out.println((int)Math.floor(entry.getValue()) + "x^" + entry.getKey());
            else
                System.out.println(entry.getValue() + "x^" + entry.getKey());
        }
    }
    public String convertToString(){
        String s = "";
        TreeMap<Integer, Double> sortedMonomials = new TreeMap<>(monomials);
        for (Integer key : sortedMonomials.descendingKeySet()){
            if(monomials.get(key) < 0) {
                if(monomials.get(key) - Math.floor(monomials.get(key)) < 0.01)
                    s += (int)Math.floor(monomials.get(key)) + ((key>0)? "x"+((key>1)? "^"+key:""): "");
                else s += Math.round(monomials.get(key)*100.0)/100.0 + ((key>0)? "x"+((key>1)? "^"+key:""): "");
            }
            else if(monomials.get(key) == 0) continue;
            else{
                if(monomials.get(key) - Math.floor(monomials.get(key)) < 0.1)
                    s += "+" + (int)Math.floor(monomials.get(key)) + ((key>0)? "x"+((key>1)? "^"+key:""): "");
                else s += "+" + Math.round(monomials.get(key)*100.0)/100.0 + ((key>0)? "x"+((key>1)? "^"+key:""): "");
            }
        }

        if(s != "" && s.charAt(0) == '+') s = s.substring(1);
        else if (s == "") s = "0";
        return s;
    }
    public String convertToString2(){
        String s = "";
        TreeMap<Integer, Double> sortedMonomials = new TreeMap<>(monomials);
        for (Integer key : sortedMonomials.descendingKeySet()){
            if(monomials.get(key) < 0) {
                if(monomials.get(key) - Math.floor(monomials.get(key)) < 0.01)
                    s += (int)Math.floor(monomials.get(key)) + "x^" + key;
                else s += Math.round(monomials.get(key)*100.0)/100.0 + "x^" + key;
            }
            else if(monomials.get(key) == 0) continue;
            else{
                if(monomials.get(key) - Math.floor(monomials.get(key)) < 0.1)
                    s += "+" + (int)Math.floor(monomials.get(key)) + "x^" + key;
                else s += "+" + Math.round(monomials.get(key)*100.0)/100.0 + "x^" + key;
            }
        }

        if(s != "" && s.charAt(0) == '+') s = s.substring(1);
        else if (s == "") s = "0";
        return s;
    }
    public Integer getMaxKey(){
        Integer maxKey = 0;
        for (int key : this.monomials.keySet()) {
            if (key > maxKey) {
                maxKey = key;
            }
        }
        return maxKey;
    }
    public static void main(String[] args ) // for tests
    {
        String poly = "+4.31921x^    500132-3x^4   +x^2   -8   x-1";
        poly = adjustPolynomialString(poly);
        Polynomial p = new Polynomial(poly);
        System.out.println(p.convertToString());
    }
}
