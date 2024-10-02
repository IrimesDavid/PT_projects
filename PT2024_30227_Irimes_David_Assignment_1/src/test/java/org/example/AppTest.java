package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.MODEL.Operation;
import org.example.MODEL.Polynomial;

public class AppTest extends TestCase
{
    Polynomial p1;
    Polynomial p2;
    public AppTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(AppTest.class);
    }

    //TESTS
    public void testAdd()
    {
        p1 = new Polynomial(Polynomial.adjustPolynomialString("4x^5-3x^4+   x^2-8x+1"));
        p2 = new Polynomial(Polynomial.adjustPolynomialString("3x^4-x^3+x^2+2x-1"));
        String res = Operation.addPoly(p1,p2);

        assertEquals("4x^5-1x^3+2x^2-6x",res);
    }
    public void testSubstract()
    {
        p1 = new Polynomial(Polynomial.adjustPolynomialString("4x^   5-3x^4+x^2-8x+1"));
        p2 = new Polynomial(Polynomial.adjustPolynomialString("3x^4-x^3+x^2+2x-1"));
        String res = Operation.substractPoly(p1,p2);

        assertEquals("4x^5-6x^4+1x^3-10x+2",res);
    }
    public void testMultiplication()
    {
        p1 = new Polynomial(Polynomial.adjustPolynomialString("3x^2-x+1"));
        p2 = new Polynomial(Polynomial.adjustPolynomialString("x-2"));
        String res = Operation.multiplyPoly(p1,p2);

        assertEquals("3x^3-7x^2+3x-2",res);
    }
    public void testDivision()
    {
        p1 = new Polynomial(Polynomial.adjustPolynomialString("x^3-2x^2+6x-5"));
        p2 = new Polynomial(Polynomial.adjustPolynomialString("x^2-1"));
        String res = Operation.dividePoly(p1,p2);

        assertEquals("1x-2   r:7x-7",res);
    }
    public void testDerivation(){
        p1 = new Polynomial(Polynomial.adjustPolynomialString("x^3-2x^2+6x-5"));
        String res = Operation.derivePoly(p1);
        assertEquals("3x^2-4x+6",res);
    }
    public void testIntegration(){
        p1 = new Polynomial(Polynomial.adjustPolynomialString("x^3-2x^2+6x-5"));
        String res = Operation.integratePoly(p1);
        assertEquals("0.25x^4-0.67x^3+3x^2-5x",res);
    }
}
