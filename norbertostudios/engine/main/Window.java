package norbertostudios.engine.main;

import norbertostudios.engine.util.Constants;

import javax.swing.*;
import java.awt.*;

////    Created     10/24/19, 7:40 AM
////    By:         Norberto Studios
////    
public class Window extends JFrame
{
    private int width = Constants.WIDTH;
    private int height = Constants.HEIGHT;
    private Canvas canvas;

    public Window()
    {
        // JFrame
        setTitle("Java Engine 1.0");
        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);



        // Canvas
        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(true);

        add(canvas);

        setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
