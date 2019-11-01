package norbertostudios.engine.input;////

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

////    Created     10/24/19, 8:12 PM
////    By:         Norberto Studios
////    
public class Keyboard implements KeyListener {

    public boolean[] keys;

    public static boolean UP, DOWN, LEFT,RIGHT, SHOOT;

    public Keyboard()
    {
        keys = new boolean[256];

        UP      = false;
        DOWN    = false;
        LEFT    = false;
        RIGHT   = false;
        SHOOT   = false;

    }

    public void update()
    {
        UP = keys[KeyEvent.VK_UP];
        DOWN = keys[KeyEvent.VK_DOWN];
        LEFT = keys[KeyEvent.VK_LEFT];
        RIGHT = keys[KeyEvent.VK_RIGHT];
        SHOOT = keys[KeyEvent.VK_C];
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("Key Pressed "+e.getKeyCode());
        keys[e.getKeyCode()] = true;
    }


    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("Key Released "+e.getKeyCode());
        keys[e.getKeyCode()] = false;
    }

}
