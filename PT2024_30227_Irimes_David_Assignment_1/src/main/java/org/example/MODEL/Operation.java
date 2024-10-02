package org.example.MODEL;

import java.util.HashMap;

public class Operation {
    public static String addPoly(Polynomial op1, Polynomial op2){
        Polynomial poly = new Polynomial();
            for (HashMap.Entry<Integer, Double> entry : op1.monomials.entrySet())
                poly.monomials.put(entry.getKey(),entry.getValue());
            for (HashMap.Entry<Integer, Double> entry : op2.monomials.entrySet())
                if(poly.monomials.containsKey(entry.getKey())){
                    Double newCoef = poly.monomials.get(entry.getKey()) + entry.getValue();
                    poly.monomials.put(entry.getKey(),newCoef);
                }else{
                    poly.monomials.put(entry.getKey(),entry.getValue());
                }
            String res = poly.convertToString();
            return res;
    }
    public static String substractPoly(Polynomial op1, Polynomial op2){
        Polynomial poly = new Polynomial();
        for (HashMap.Entry<Integer, Double> entry : op1.monomials.entrySet())
            poly.monomials.put(entry.getKey(),entry.getValue());
        for (HashMap.Entry<Integer, Double> entry : op2.monomials.entrySet())
            if(poly.monomials.containsKey(entry.getKey())){
                Double newCoef = poly.monomials.get(entry.getKey()) - entry.getValue();
                poly.monomials.put(entry.getKey(),newCoef);
            }else{
                poly.monomials.put(entry.getKey(),-entry.getValue());
            }
        String res = poly.convertToString();
        return res;
    }
    public static Polynomial substractPoly2(Polynomial op1, Polynomial op2){
        Polynomial poly = new Polynomial();
        for (HashMap.Entry<Integer, Double> entry : op1.monomials.entrySet())
            poly.monomials.put(entry.getKey(),entry.getValue());
        for (HashMap.Entry<Integer, Double> entry : op2.monomials.entrySet())
            if(poly.monomials.containsKey(entry.getKey())){
                Double newCoef = poly.monomials.get(entry.getKey()) - entry.getValue();
                poly.monomials.put(entry.getKey(),newCoef);
            }else{
                poly.monomials.put(entry.getKey(),-entry.getValue());
            }
        return poly;
    }
    public static String integratePoly(Polynomial poly){
       Polynomial intPoly = new Polynomial();
        for (HashMap.Entry<Integer, Double> entry : poly.monomials.entrySet())
            intPoly.monomials.put(entry.getKey() + 1, entry.getValue()/(entry.getKey()+1));
        String res = intPoly.convertToString();
        return res;
    }
    public static String derivePoly(Polynomial poly){
        Polynomial divPoly = new Polynomial();
        for (HashMap.Entry<Integer, Double> entry : poly.monomials.entrySet())
            divPoly.monomials.put(entry.getKey() - 1,entry.getValue()*entry.getKey());
        String res = divPoly.convertToString();
        return res;
    }

    public  static String multiplyPoly(Polynomial op1, Polynomial op2){
        Polynomial mulPoly = new Polynomial();
        for (HashMap.Entry<Integer, Double> entry1 : op1.monomials.entrySet()){
            for (HashMap.Entry<Integer, Double> entry2 : op2.monomials.entrySet()){
                if(mulPoly.monomials.containsKey(entry1.getKey()+entry2.getKey())){
                    mulPoly.monomials.put(entry1.getKey()+entry2.getKey(),mulPoly.monomials.get(entry1.getKey()+entry2.getKey())+
                            entry1.getValue()*entry2.getValue());
                }else{
                    mulPoly.monomials.put(entry1.getKey()+entry2.getKey(),Math.round((entry1.getValue()*entry2.getValue())*100.0)/100.0);
                }
            }
        }
        String res = mulPoly.convertToString();
        return res;
    }
    public  static String dividePoly(Polynomial op1, Polynomial op2){
        Polynomial divPoly = new Polynomial();
        Polynomial semiResult = new Polynomial();

        if(op1.getMaxKey()< op2.getMaxKey()) {
            System.out.println("EXCEPTION: <DivideException>");
            return "Deimpartitul nu poate avea grad mai mic decat impartitorul.";
        }

        Double coef = (op1.monomials.get(op1.getMaxKey())/op2.monomials.get(op2.getMaxKey()));
        Integer grade = op1.getMaxKey() - op2.getMaxKey();
        divPoly.monomials.put(grade,coef);
        for (Integer key : op2.monomials.keySet())
            semiResult.monomials.put(grade+key,coef*op2.monomials.get(key));
        semiResult = Operation.substractPoly2(op1,semiResult);

        while(true){
            String s = semiResult.convertToString2();
//            Polynomial.adjustPolynomialString(s);
            Polynomial newOp1 = new Polynomial(s);

            if (newOp1.monomials.isEmpty()) break;
            Double coef1 = (newOp1.monomials.get(newOp1.getMaxKey())/op2.monomials.get(op2.getMaxKey()));
            Integer grade1 = newOp1.getMaxKey() - op2.getMaxKey();
            if(grade1<0) break;
            divPoly.monomials.put(grade1,coef1);

            semiResult.monomials.clear();
            for (Integer key : op2.monomials.keySet())
                semiResult.monomials.put(grade1+key,coef1*op2.monomials.get(key));
            semiResult = Operation.substractPoly2(newOp1,semiResult);
        }

        String res = divPoly.convertToString() + "   r:" + semiResult.convertToString();
        return res;
    }
}
