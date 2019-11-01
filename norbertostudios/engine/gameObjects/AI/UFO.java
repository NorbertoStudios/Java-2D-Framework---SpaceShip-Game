package norbertostudios.engine.gameObjects.AI;////

import norbertostudios.engine.gameObjects.Chronometer;
import norbertostudios.engine.gameObjects.Laser;
import norbertostudios.engine.gameObjects.MovingObject;
import norbertostudios.engine.graphics.Assets;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.states.GameState;
import norbertostudios.engine.util.Constants;
import norbertostudios.engine.util.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

////    Created     10/28/19, 6:55 PM
////    By:         Norberto Studios
////    
public class UFO extends MovingObject {

    private ArrayList<Vector2D> path;

    private Vector2D currentNode;
    private int index;
    private boolean following = true;

    private Chronometer fireRate;

    private Sound shoot, ufoExplosion;

    public UFO(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture,
               ArrayList<Vector2D> path, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);

        this.path = path;
        index = 0;
        following = true;

        fireRate = new Chronometer();
        fireRate.run(Constants.UFO_FIRE_RATE);

        shoot = new Sound(Assets.ufoShoot);
        ufoExplosion = new Sound(Assets.ufoExplosion);
    }

    private Vector2D pathToFollow() {
        currentNode = path.get(index);

        double distanceToNode = currentNode.sub(getCenter()).getMagnitude();

        if (distanceToNode < Constants.NODE_RADIUS) {
            index++;
            if (index >= path.size()) {
                following = false;
            }
        }

        return seekForce(currentNode);
    }

    private Vector2D seekForce(Vector2D target) {

        Vector2D desiredVelocity = target.sub(getCenter());
        desiredVelocity = desiredVelocity.normalize().scale(maxVel);

//        System.out.println(desiredVelocity.getX() - velocity.getX());
        return desiredVelocity.sub(velocity);
    }

    @Override
    public void update() {
        Vector2D pathFollowing;

        if (following) {
            pathFollowing = pathToFollow();

        } else {
            pathFollowing = new Vector2D();
        }

        pathFollowing = pathFollowing.scale(1 / Constants.UFO_MASS);

        velocity = velocity.add(pathFollowing);

        velocity = velocity.limit(maxVel);

        position = position.add(velocity);


        if (position.getX() > Constants.WIDTH || position.getY() > Constants.HEIGHT
                || position.getX() < 0 || position.getY() < 0)
            destroy();

        // shoot

        if (!fireRate.isRunning()) {

            Vector2D toPlayer = gameState.getPlayer().getCenter().sub(getCenter());

            toPlayer = toPlayer.normalize();

            double currentAngle = toPlayer.getAngle();

            currentAngle += Math.random() * (Math.PI/2) - (Math.PI/2) / 2 + currentAngle;

            if(toPlayer.getX() < 0)
                currentAngle = -currentAngle + Math.PI;

            toPlayer = toPlayer.setDirection(currentAngle);

            Laser laser = new Laser(
                    getCenter().add(toPlayer.scale(width)),
                    toPlayer,
                    Constants.LASER_VEL,
                    currentAngle + Math.PI / 2,
                    Assets.blueLaser,
                    gameState
            );

            gameState.getMovingObjectArrayList().add(0, laser);

            fireRate.run(Constants.UFO_FIRE_RATE);

            shoot.play();

        }

        if(shoot.getFramePosition() > 8500)
        {
            shoot.stop();
        }

        angle += Constants.UFO_ANGLE;

        collideWith();
        fireRate.update();

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(texture, at, null);

        g.setColor(Color.RED);

        for (int i = 0; i < path.size(); i++) {
            g.drawRect((int) path.get(i).getX(), (int) path.get(i).getY(), 5, 5);
        }

    }

    @Override
    protected void destroy() {

        gameState.getHud().addScore(Constants.UFO_SCORE, position,gameState);

        super.destroy();

        ufoExplosion.play();
    }
}
