package norbertostudios.engine.graphics;////

import norbertostudios.engine.math.Vector2D;

import java.awt.image.BufferedImage;

////    Created     10/28/19, 6:22 PM
////    By:         Norberto Studios
////    
public class Animation {
    private BufferedImage[] frames;
    private int velocity;
    private int index;
    private boolean running;

    private Vector2D position;

    //Auxiliar
    private long time, lastTime;

    public Animation(BufferedImage[] frames, int velocity, Vector2D position) {
        this.frames = frames;
        this.velocity = velocity;
        this.position = position;

        index = 0;
        running = true;
        time = 0;
        lastTime = System.currentTimeMillis();

    }

    public void update() {
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (time > velocity) {
            time = 0;
            index++;
            if (index >= frames.length) {
                running = false;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2D getPosition() {
        return position;
    }

    public BufferedImage getCurrentFrames() {
        return frames[index];
    }
}
