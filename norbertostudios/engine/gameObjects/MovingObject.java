package norbertostudios.engine.gameObjects;////

import norbertostudios.engine.gameObjects.creature.Player;
import norbertostudios.engine.graphics.Assets;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.states.GameState;
import norbertostudios.engine.util.Sound;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

////    Created     10/24/19, 8:44 PM
////    By:         Norberto Studios
////    
public abstract class MovingObject extends GameObject {
    protected Vector2D velocity;
    protected AffineTransform at;
    protected double angle;
    protected double maxVel;

    protected int width;
    protected int height;
    protected GameState gameState;



    public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVel = maxVel;
        this.gameState = gameState;

        width = texture.getWidth();
        height = texture.getHeight();

        angle = 0;


    }

    protected void collideWith() {

        ArrayList<MovingObject> movingObjects = gameState.getMovingObjectArrayList();

        for (int i = 0; i < movingObjects.size(); i++) {

            MovingObject m = movingObjects.get(i);

            if (m.equals(this)) {
                continue;
            }

            double distance = m.getCenter().sub(getCenter()).getMagnitude();

            if (distance < m.width / 2 + width / 2 && movingObjects.contains(this)) {
                objectCollision(m, this);
            }
        }
    }

    private void objectCollision(MovingObject a, MovingObject b)
    {
        if(a instanceof Player && ((Player) a).isSpawning())
        {
            return;
        }
        if(b instanceof Player && ((Player) b).isSpawning())
        {
            return;
        }
        if (!(a instanceof Meteor && b instanceof Meteor)) {
            gameState.playExplosion(getCenter());
            a.destroy();
            b.destroy();
        }
    }

    protected void destroy() {
        gameState.getMovingObjectArrayList().remove(this);
    }


    public Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }
}
