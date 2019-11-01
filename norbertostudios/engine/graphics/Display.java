package norbertostudios.engine.graphics;////

import java.awt.*;
import java.awt.image.BufferStrategy;

import norbertostudios.engine.input.Keyboard;
import norbertostudios.engine.main.Window;
import norbertostudios.engine.states.GameState;

////    Created     10/24/19, 6:34 PM
////    By:         Norberto Studios
////    
public class Display
{
    private Window window;
    private GameState gameState;
    private Keyboard keyboard;

    private Graphics g;

    BufferStrategy bufferStrategy;

    public Display()
    {
        window          = new Window();
        gameState       = new GameState();
        keyboard        = new Keyboard();

        window.getCanvas().addKeyListener(keyboard);

        startStrategy();
    }

    private void startStrategy()
    {
        window.getCanvas().createBufferStrategy(3);

        bufferStrategy = window.getCanvas().getBufferStrategy();
    }

    public void rendering() {
        do {
            do {
                g = null;
                try {
                    g = bufferStrategy.getDrawGraphics();
                    g.clearRect(0, 0, window.getWidth(), window.getHeight());
                    render(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    public void update()
    {
        keyboard.update();
        gameState.update();
    }




    //////////////////////////////////////------- Here we Display the Graphics
    public void render(Graphics g)
    {
        update();
////        g.fillRect(0,0,window.getWidth(),window.getHeight());
        g.drawImage(Assets.background, 0,0,window.getWidth(),window.getHeight(),null);
//        g.drawImage(Assets.player, 100,100,null);
        gameState.draw(g);

    }
}
