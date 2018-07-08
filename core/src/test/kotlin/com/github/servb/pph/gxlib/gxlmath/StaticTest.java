package com.github.servb.pph.gxlib.gxlmath;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@code com.github.servb.gxlib.gxlmath.Static} test class.
 *
 * @author SerVB
 */
public final class StaticTest {

    /**
     * Test of PI constant, of class Static.
     */
    @Test
    public void testPi() {
        System.out.println("PI");

        {
            final float expResult = (float) Math.PI;
            final float result = Static.PI;
            assertEquals(expResult, result, 1e-6);
        }
    }

    /**
     * Test of int_sqrt method, of class Static.
     */
    @Test
    public void testInt_sqrt() {
        System.out.println("int_sqrt");

        {
            final int n = 0;
            final int expResult = 0;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 1;
            final int expResult = 1;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 2;
            final int expResult = 1;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 3;
            final int expResult = 1;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 4;
            final int expResult = 2;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 5;
            final int expResult = 2;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 6;
            final int expResult = 2;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 7;
            final int expResult = 2;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 8;
            final int expResult = 2;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 9;
            final int expResult = 3;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }

        {
            final int n = 10;
            final int expResult = 3;
            final int result = Static.int_sqrt(n);
            assertEquals(expResult, result);
        }
    }

}
