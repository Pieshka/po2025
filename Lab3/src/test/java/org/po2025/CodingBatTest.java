package org.po2025;

import static org.junit.Assert.*;

public class CodingBatTest
{

    @org.junit.Test
    public void sleepIn()
    {
        assertFalse(CodingBat.sleepIn(true, false));
        assertTrue(CodingBat.sleepIn(true, true));
        assertTrue(CodingBat.sleepIn(false, true));
        assertTrue(CodingBat.sleepIn(false, false));
    }

    @org.junit.Test
    public void monkeyTrouble()
    {
        assertTrue(CodingBat.monkeyTrouble(true,true));
        assertTrue(CodingBat.monkeyTrouble(false, false));
        assertFalse(CodingBat.monkeyTrouble(true,false));
        assertFalse(CodingBat.monkeyTrouble(false, true));
    }

    @org.junit.Test
    public void sumDouble()
    {
        assertEquals(8,CodingBat.sumDouble(2, 2));
        assertEquals(3, CodingBat.sumDouble(1, 2));
        assertEquals(0, CodingBat.sumDouble(0, 0));
    }

    @org.junit.Test
    public void countEvens()
    {
        assertEquals(4,CodingBat.countEvens(new int[]{1, 2, 5, 6, 7, 9, 4, 8}));
        assertEquals(0,CodingBat.countEvens(new int[]{1,1,1,1,1,1}));
        assertEquals(5,CodingBat.countEvens(new int[]{2,2,2,2,2}));
    }

    @org.junit.Test
    public void bigDiff()
    {
        assertEquals(8,CodingBat.bigDiff(new int[]{1, 2, 5, 6, 7, 9, 4, 8}));
        assertEquals(0,CodingBat.bigDiff(new int[]{1, 1, 1, 1, 1, 1, 1, 1}));
        assertEquals(92,CodingBat.bigDiff(new int[]{89,2,34,2,54,94}));
    }

    @org.junit.Test
    public void helloName()
    {
        assertEquals("Hello Eva!",CodingBat.helloName("Eva"));
        assertEquals("Hello X!",CodingBat.helloName("X"));
        assertEquals("Hello Emilia!!",CodingBat.helloName("Emilia!"));
    }
}