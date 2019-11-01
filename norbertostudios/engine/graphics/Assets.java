package norbertostudios.engine.graphics;////

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;

////    Created     10/24/19, 6:17 PM
////    By:         Norberto Studios
////    
public class Assets {
    public static BufferedImage player;
    public static BufferedImage background;

    //Player
    // HUD

    public static BufferedImage playerLife;
    public static BufferedImage[] numerals = new BufferedImage[11];

    //Font
    public static Font fontBig;
    public static Font fontMed;

    // Effects
    public static BufferedImage speed;
    public static BufferedImage backSpeed;

    // Lasers
    public static BufferedImage blueLaser;
    public static BufferedImage redLaser;
    public static BufferedImage greenLaser;

    //Sounds
    public static Clip backgroundMusic, meteorExplosion, ufoExplosion, playerLoose, playerShoot, ufoShoot;

    // Meteors
    public static BufferedImage[] bigs = new BufferedImage[4];
    public static BufferedImage[] meds = new BufferedImage[2];
    public static BufferedImage[] smalls = new BufferedImage[2];
    public static BufferedImage[] tinies = new BufferedImage[2];

    // Explotion
    public static BufferedImage[] explotion = new BufferedImage[9];

    // AI
    // UFO
    public static BufferedImage ufo;


    public static void init() {
        // BG
        background = Loader.ImageLoader("/Backgrounds/darkPurple.png");
        // player
        player = Loader.ImageLoader("/Ships/playerShip1_red.png");
        playerLife = Loader.ImageLoader("/UI/playerLife1_red.png");

        for (int i = 0; i < numerals.length; i++) {
            numerals[i] = Loader.ImageLoader("/UI/numeral" + (i) + ".png");

        }

        // font
        fontBig = Loader.loadFont("/Fonts/kenvector_future.ttf", 42);
        fontMed = Loader.loadFont("/Fonts/kenvector_future.ttf", 20);

        // effect
        speed = Loader.ImageLoader("/Effects/fire10.png");
        backSpeed = Loader.ImageLoader("/Effects/fire13.png");

        // lasers
        blueLaser = Loader.ImageLoader("/Lasers/laserBlue01.png");
        redLaser = Loader.ImageLoader("/Lasers/laserRed01.png");
        greenLaser = Loader.ImageLoader("/Lasers/laserGreen11.png");

        // Meteors
        for (int i = 0; i < bigs.length; i++) {
            bigs[i] = Loader.ImageLoader("/Meteors/meteorBrown_big" + (i + 1) + ".png");
        }
        for (int i = 0; i < meds.length; i++) {
            meds[i] = Loader.ImageLoader("/Meteors/meteorBrown_med" + (i + 1) + ".png");
        }
        for (int i = 0; i < smalls.length; i++) {
            smalls[i] = Loader.ImageLoader("/Meteors/meteorBrown_small" + (i + 1) + ".png");
        }
        for (int i = 0; i < tinies.length; i++) {
            tinies[i] = Loader.ImageLoader("/Meteors/meteorBrown_tiny" + (i + 1) + ".png");
        }

        // Explotion
        for (int i = 0; i < explotion.length; i++) {
            explotion[i] = Loader.ImageLoader("/Explosion/" + i + ".png");
        }

        // AI

        //UFO
        ufo = Loader.ImageLoader("/Ufos/ufoBlue.png");

        // Sound
        backgroundMusic = Loader.loadClip("/Sound/BG2.wav");
        playerLoose = Loader.loadClip("/Sound/sfx_lose.wav");
        playerShoot = Loader.loadClip("/Sound/sfx_laser1.wav");
        ufoShoot = Loader.loadClip("/Sound/sfx_laser2.wav");
        ufoExplosion = Loader.loadClip("/Sound/Ufo_explosion.wav");
        meteorExplosion = Loader.loadClip("/Sound/meteor_explosion.wav");

    }

}
