package norbertostudios.engine.gameObjects;////

import norbertostudios.engine.graphics.Assets;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.states.GameState;
import norbertostudios.engine.util.Constants;
import norbertostudios.engine.util.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

////    Created     10/28/19, 8:36 AM
////    By:         Norberto Studios
////    
public class Meteor extends MovingObject
{

    private Size size;

    private Sound meteorExplosion;

    public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size = size;

        this.velocity = velocity.scale(maxVel);

        meteorExplosion= new Sound(Assets.meteorExplosion);
    }

    @Override
    public void update() {
        position = position.add(velocity);

        limitScreen();

        angle += Constants.DELTAANGLE/2;

        if(meteorExplosion.getFramePosition() > 9000)
        {
            meteorExplosion.stop();
        }
    }

    @Override
    public void destroy()
    {
        gameState.getHud().addScore(Constants.METEOR_SCORE,position,gameState);
        gameState.divideMeteor(this);
        super.destroy();
        meteorExplosion.play();
    }

    @Override
    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(texture, at, null);

    }

    public void limitScreen() {
        if (position.getX() > Constants.WIDTH) {
            position.setX(-width);
        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(-height);
        }
        if (position.getX() < -width) {
            position.setX(Constants.WIDTH);
        }
        if (position.getY() < -height) {
            position.setY(Constants.HEIGHT);
        }
    }

    public Size getSize()
    {
        return size;
    }
}
