package norbertostudios.engine.gameObjects.creature;////

import norbertostudios.engine.gameObjects.Laser;
import norbertostudios.engine.gameObjects.Chronometer;
import norbertostudios.engine.gameObjects.MovingObject;
import norbertostudios.engine.graphics.Assets;
import norbertostudios.engine.input.Keyboard;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.states.GameState;
import norbertostudios.engine.util.Constants;
import norbertostudios.engine.util.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

////    Created     10/24/19, 7:32 PM
////    By:         Norberto Studios
////    
public class Player extends MovingObject {

    private Vector2D heading; // direccion donde la nave esta mirando
    private Vector2D acceleration;

    private final double PI = Math.PI;

    private boolean isAccelerating = false;
    private boolean isBacking = false;
    private boolean isLeftPress = false;
    private boolean isRightPress = false;

    private Chronometer fireRate;

    private boolean spawning, visible;

    private Chronometer spawnTime, flickerTime;

    private Sound shoot, playerLose;


    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);

        heading = new Vector2D(0, 1);

        acceleration = new Vector2D();

        fireRate = new Chronometer();
        spawnTime = new Chronometer();
        flickerTime = new Chronometer();

        shoot = new Sound(Assets.playerShoot);
        playerLose = new Sound(Assets.playerLoose);

    }

    @Override
    public void update() {

        if(!spawnTime.isRunning())
        {
            spawning = false;
            visible = true;
        }

        if(spawning)
        {
            if(!flickerTime.isRunning())
            {
                flickerTime.run(Constants.FLICKER_TIME);
                visible = !visible;

            }
        }

        if (Keyboard.SHOOT && !fireRate.isRunning() && !spawning) {
            gameState.getMovingObjectArrayList().add(
                    0, new Laser(
                            getCenter().add(heading.scale(width)),
                            heading,
                            Constants.LASER_VEL,
                            angle,
                            Assets.redLaser,
                            gameState));

            fireRate.run(Constants.FIRERATE);
            shoot.play();
        }

        if(shoot.getFramePosition() > 8500)
        {
            shoot.stop();
        }

        if (Keyboard.RIGHT) {
            isRightPress = true;
            angle += Constants.DELTAANGLE;
        }
        else {
            isRightPress = false;
        }
        if (Keyboard.LEFT) {
            isLeftPress = true;
            angle -= Constants.DELTAANGLE;}else {
            isLeftPress = false;

        }

        if (Keyboard.UP) {
            acceleration = heading.scale(Constants.ACC);
            isAccelerating = true;
            isBacking = false;
        } else {
            isAccelerating = false;

            if (Keyboard.DOWN) {
                isBacking = true;
                acceleration = heading.scale(-Constants.BACC);
            } else if (velocity.getMagnitude() != 0) {

                isBacking=false;
                acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC / 2);
            }
        }

        velocity = velocity.add(acceleration);

        velocity = velocity.limit(maxVel);

        // rotation Direction
        heading = heading.setDirection(angle - (PI / 2));

        position = position.add(velocity);

        limitScreen();

        fireRate.update();
        spawnTime.update();
        flickerTime.update();
        collideWith();
    }

    public void limitScreen() {
        if (position.getX() > Constants.WIDTH) {
            position.setX(0);
        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(0);
        }
        if (position.getX() < 0) {
            position.setX(Constants.WIDTH);
        }
        if (position.getY() < 0) {
            position.setY(Constants.HEIGHT);
        }
    }

    @Override
    protected void destroy() {
        spawning = true;
        spawnTime.run(Constants.SPAWNING_TIME);
        playerLose.play();
        resetValue();
        gameState.getHud().delLives();

    }

    @Override
    public void draw(Graphics g) {

        if(!visible)
            return;

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 16, position.getY() + height / 2 + 16);
        AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 16, position.getY() + height / 2 + 16);

        at1.rotate(angle, -16, -16);
        at2.rotate(angle, width / 2 - 16, -16);

        if (isAccelerating) {
            g2d.drawImage(Assets.speed, at1, null);
            g2d.drawImage(Assets.speed, at2, null);
        }

        AffineTransform at1b = AffineTransform.getTranslateInstance(position.getX() + width / 2 - 12, position.getY() + height / 2 - 69);
        AffineTransform at2b = AffineTransform.getTranslateInstance(position.getX() + 48, position.getY() + height / 2 - 68);

        at1b.rotate(angle + 0.4, +12, +69);
        at2b.rotate(angle - 0.4 , width / 2 - 48, +68);

        if (isBacking) {
            g2d.drawImage(Assets.backSpeed, at1b, null);
            g2d.drawImage(Assets.backSpeed, at2b, null);
        }

        AffineTransform atLeft = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 40 , position.getY() + height / 2 - 20);
        AffineTransform atRight = AffineTransform.getTranslateInstance(position.getX() - 3, position.getY() + height / 2 - 20);

        atRight.rotate(angle + 0.4, width / 2 + 3, +20);
        atLeft.rotate(angle - 0.4, -40 , +20);

        if (isRightPress) {
            g2d.drawImage(Assets.backSpeed, atLeft, null);
        }
        if(isLeftPress)
        {
            g2d.drawImage(Assets.backSpeed, atRight, null);
        }

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(texture, at, null);

    }

    public void resetValue()
    {
        angle = 0;
        velocity = new Vector2D();
        position = GameState.PLAYER_START_POSITION;
    }

    public boolean isSpawning() {
        return spawning;
    }
}
