package norbertostudios.engine.graphics;////

import norbertostudios.engine.gameObjects.Message;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.states.GameState;
import norbertostudios.engine.util.Constants;

import java.awt.*;

////    Created     10/30/19, 8:56 AM
////    By:         Norberto Studios
////    
public class HUD {
    private int score = 0;
    private int lives = 3;


    public void addScore(int value, Vector2D position, GameState gameState) {
        gameState.getMessages().add(new Message(position, true,
                "+"+value+" score", Color.WHITE, false
        , Assets.fontMed,gameState));
        score += value;
    }

    public void addLives(int value) {
        lives += value;
    }

    public int getLives() {
        return lives;
    }

    public void delLives() {
        lives--;
    }

    public void drawLives(Graphics g) {
        Vector2D livesPos = new Vector2D(25, 25);

        g.drawImage(Assets.playerLife, (int) livesPos.getX(), (int) livesPos.getY(), null);

        g.drawImage(Assets.numerals[10], (int) livesPos.getX() + 40, (int) livesPos.getY() +5, null);

        String livesToString = Integer.toString(lives);

        Vector2D pos = new Vector2D(livesPos.getX(), livesPos.getY());

        for (int i = 0; i < livesToString.length(); i++) {

           int number = Integer.parseInt(livesToString.substring(i,i +1));

           if(number <= 0)
           {
               break;
           }
            g.drawImage(Assets.numerals[number],
                    (int) pos.getX() + 60, (int) pos.getY() + 5, null);
            pos.setX(pos.getX() + 20);
        }


    }

    public void drawScore(Graphics g) {

        Vector2D pos = new Vector2D(850, 25);

        String scoreToString = Integer.toString(score);

        for (int i = 0; i < scoreToString.length(); i++) {
            g.drawImage(Assets.numerals[Integer.parseInt(scoreToString.substring(i, i + 1))],
                    (int) pos.getX(), (int) pos.getY(), null);
            pos.setX(pos.getX() + 20);
        }
    }

}
