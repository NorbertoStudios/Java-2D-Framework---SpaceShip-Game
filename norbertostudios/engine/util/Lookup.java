package norbertostudios.engine.util;////

////    Created     10/24/19, 9:00 PM
////    By:         Norberto Studios
////    
public class Lookup
{
    public final static double[] SIN = calculateSin();
    public final static double[] COS = calculateCos();


    public static double[] calculateSin()
    {
        double[] sin = new double[360];

        for (int i = 0; i < 360; i++)
        {
            sin[i] = Math.sin(i*Math.PI/180);
        }
        return sin;
    }
    public static double[] calculateCos()
    {
        double[] cos = new double[360];

        for (int i = 0; i < 360; i++)
        {
            cos[i] = Math.cos(i*Math.PI/180);
        }
        return cos;
    }
}
