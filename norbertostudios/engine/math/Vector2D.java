package norbertostudios.engine.math;////

import norbertostudios.engine.util.Lookup;

////    Created     10/24/19, 7:27 PM
////    By:         Norberto Studios
////    
public class Vector2D
{
    private double x;
    private double y;

    public Vector2D(double x, double y)
    {
        this.x = x;
        this.y = y;

    }
        // Vector Zero
    public Vector2D()
    {
        x = 0;
        y = 0;
    }

    public Vector2D add(Vector2D v)
    {
        return new Vector2D(x + v.getX(), y + v.getY());
    }
    public Vector2D sub(Vector2D v)
    {
        return new Vector2D(x - v.getX(), y - v.getY());
    }

    public Vector2D scale(double value)
    {
        return new Vector2D(x * value,y * value);
    }

//    public void limit(double value)
//    {
//        if(getMagnitude() > value) {
//            normalize().scale(value);
//        }
//
//    }
    public Vector2D limit(double value)
    {
        if(getMagnitude() > value) {
           return this.normalize().scale(value);
        }
        return this;

    }

    public double getAngle() {
        return Math.asin(y/getMagnitude());
    }


    public Vector2D normalize() // vector unitario 1
    {
        double magnitude = getMagnitude();
        return new Vector2D(x/magnitude, y/magnitude);
    }


    public double getMagnitude()
    {
        return Math.sqrt(x*x + y*y);
    }

    public Vector2D setDirection(double angle)
    {
        double magnitude = getMagnitude();
        return new Vector2D(Math.cos(angle)*magnitude, Math.sin(angle)*magnitude);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
