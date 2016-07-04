package test;

import java.util.List;

public class TestClass {

    private int x;
    public int y;

    public strictfp float testMethod()
    {
        return x+y;
    }

    public TestClass()
    {
        x=10;
        y=x+4;
    }
}