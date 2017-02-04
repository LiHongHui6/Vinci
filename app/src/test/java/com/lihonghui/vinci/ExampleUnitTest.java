package com.lihonghui.vinci;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Calculator mCalculator;

    @Before
    public void setUp(){
        mCalculator = new Calculator();
    }

    @Test
    public void testAdd(){
        int result = mCalculator.add(1, 3);
        Assert.assertEquals(4, result);
    }

    @Test
    public void testSubtract(){
        int result  =  mCalculator.subtract(6, 4);
        Assert.assertEquals(2, result);
    }
}