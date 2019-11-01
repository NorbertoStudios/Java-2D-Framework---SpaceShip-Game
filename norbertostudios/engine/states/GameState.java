package norbertostudios.engine.states;////

import norbertostudios.engine.gameObjects.*;
import norbertostudios.engine.gameObjects.AI.UFO;
import norbertostudios.engine.gameObjects.creature.Player;
import norbertostudios.engine.graphics.Animation;
import norbertostudios.engine.graphics.Assets;
import norbertostudios.engine.graphics.HUD;
import norbertostudios.engine.graphics.Text;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.util.Constants;
import norbertostudios.engine.util.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

////    Created     10/24/19, 7:17 PM
////    By:         Norberto Studios
////    
public class GameState {
    private Player player;

    public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
            Constants.HEIGHT/2 - Assets.player.getHeight()/2);

    private ArrayList<MovingObject> movingObjectArrayList = new ArrayList<MovingObject>();

    private ArrayList<Animation> explotion = new ArrayList<Animation>();

    private ArrayList<Message> messages = new ArrayList<Message>();


    private int meteors;

    protected HUD hud;

    private int wave = 1;

    private Sound backgroundSound;


    public GameState() {


        player = new Player(PLAYER_START_POSITION, new Vector2D(), Constants.PLAYER_MAX_VEL, Assets.player, this);
        movingObjectArrayList.add(player);

        hud = new HUD();
        meteors = 1;

        startWave();
        backgroundSound = new Sound(Assets.backgroundMusic);
        backgroundSound.loop();
        backgroundSound.changeVolume(-10f);

    }

    public HUD getHud() {
        return hud;
    }

    public void divideMeteor(Meteor meteor)
    {
        Size size = meteor.getSize();

        BufferedImage[] textures = size.textures;

        Size newSize = null;

        switch (size)
        {
            case BIG:
                newSize = Size.MED;
                break;
            case MED:
                newSize = Size.SMALL;
                break;
            case SMALL:
                newSize = Size.TINY;
                break;
            default:
                return;
        }

        for (int i = 0; i < size.quantity; i++) {

            movingObjectArrayList.add(new Meteor(
                    meteor.getPosition(),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Constants.METEOR_VEL * Math.random() + 1,
                    textures[(int) (Math.random() * textures.length)],
                    this,
                    newSize
            ));
        }
    }

    private void startWave() {

        messages.add(new Message(new Vector2D(Constants.WIDTH/2,Constants.HEIGHT/2), false,
                "WAVE "+wave, Color.WHITE,true,Assets.fontBig,this));

        double x, y;

        for (int i = 0; i < meteors; i++) {
            x = i % 2 == 0 ? Math.random() * Constants.WIDTH : 0;
            y = i % 2 == 0 ? 0 : Math.random() * Constants.HEIGHT;

            BufferedImage texture = Assets.bigs[(int) (Math.random() * Assets.bigs.length)];

            movingObjectArrayList.add(new Meteor(
                    new Vector2D(x, y),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Constants.METEOR_VEL * Math.random() + 1,
                    texture,
                    this,
                    Size.BIG
            ));
        }

        meteors++;
        spawnUFO();
        System.out.println("Spawn");
    }


    public void playExplosion(Vector2D position)
    {
        explotion.add(new Animation(
                Assets.explotion,
                Constants.EXPLOTION_FRAMES,
                position.sub(new Vector2D(
                        Assets.explotion[0].getWidth()/2,
                        Assets.explotion[0].getHeight()/2
                ))));
    }

    private void spawnUFO()
    {
        int rand = (int) (Math.random()*2);

        double x = rand == 0 ? (Math.random() * Constants.WIDTH) : 0;
        double y = rand == 0 ? 0 : (Math.random() * Constants.HEIGHT);

        ArrayList<Vector2D> path = new ArrayList<Vector2D>();

        double posX, posY;

        //Top Left
        posX = Math.random()*Constants.WIDTH/2;
        posY = Math.random()*Constants.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        //Top Rigth
        posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
        posY = Math.random()*Constants.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        //Bottom Left
        posX = Math.random()*Constants.WIDTH/2;
        posY = Math.random()* (Constants.HEIGHT/2) + Constants.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        // Bottom right
        posX = Math.random()* (Constants.WIDTH/2) + Constants.WIDTH/2;
        posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;
        path.add(new Vector2D(posX,posY));

        movingObjectArrayList.add(new UFO(
                new Vector2D(x,y),
                new Vector2D(),
                Constants.UFO_MAX_VEL,
                Assets.ufo,
                path,
                this
        ));

    }

    public void gameOver()
    {
        backgroundSound.stop();
        messages.add(new Message(new Vector2D(Constants.WIDTH/2,Constants.HEIGHT/2), true,
                "GAME OVER", Color.WHITE,true,Assets.fontBig,this));
    }

    public void update() {

        if(hud.getLives() == 0)
        {
            System.out.println("GAME OVER!!");

            gameOver();

        }


        for (int i = 0; i < movingObjectArrayList.size(); i++) {
            movingObjectArrayList.get(i).update();
        }

        for (int i = 0; i < explotion.size(); i++) {
           Animation anim = explotion.get(i);
           anim.update();
           if(!anim.isRunning())
           {
               explotion.remove(i);
           }
        }

        for (int i = 0; i < movingObjectArrayList.size(); i++) {
            if (movingObjectArrayList.get(i) instanceof Meteor) {
                return;
            }

        }



        startWave();



    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Anti alliasing
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).draw(g2d);
        }

        for (int i = 0; i < movingObjectArrayList.size(); i++) {
            movingObjectArrayList.get(i).draw(g);
        }


        for (int i = 0; i < explotion.size(); i++) {
            Animation anim = explotion.get(i);
            g2d.drawImage(anim.getCurrentFrames(),(int)anim.getPosition().getX(),(int)anim.getPosition().getY(), null);
        }

        hud.drawScore(g);
        hud.drawLives(g);

    }

    public ArrayList<MovingObject> getMovingObjectArrayList() {
        return movingObjectArrayList;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Player getPlayer() {
        return player;
    }
}
