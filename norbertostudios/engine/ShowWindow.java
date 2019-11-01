package norbertostudios.engine;

import norbertostudios.engine.main.RunGame;
import norbertostudios.engine.main.Window;

import javax.swing.*;

////    Created     10/24/19, 7:44 AM
////    By:         Norberto Studios
////    
public class ShowWindow
{
    public static void main(String[] args) {
        new ShowWindow();

    }

    private ShowWindow()
    {
        SwingUtilities.invokeLater(() -> new RunGame());
    }
}
