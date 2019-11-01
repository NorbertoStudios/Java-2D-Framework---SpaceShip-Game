package norbertostudios.engine.gameObjects;////

import norbertostudios.engine.graphics.Text;
import norbertostudios.engine.math.Vector2D;
import norbertostudios.engine.states.GameState;

import java.awt.*;

////    Created     10/30/19, 11:31 AM
////    By:         Norberto Studios
////    
public class Message
{
    private GameState gameState;
    private float alpha;
    private String text;
    private Vector2D position;
    private Color color;
    private boolean center;
    private  boolean fade;
    private Font font;
    private final float deltaApha = 0.01f;


    public Message(Vector2D position, boolean fade, String text,
                   Color color, boolean center, Font font, GameState gameState)
    {
        this.font = font;
        this.gameState = gameState;
        this.text = text;
        this.position = position;
        this.fade = false;
        this.center = center;
        this.color = color;

        if(fade)
            alpha = 1;
        else
            alpha = 0;
    }

    public void draw(Graphics2D g2d)
    {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));

        Text.drawText(g2d,text,position,center,color,font);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1));

        position.setY(position.getY() - 1);

        if(fade)
            alpha -= deltaApha;
        else
            alpha += deltaApha;

        if(fade && alpha < 0)
        {
            gameState.getMessages().remove(this);
        }

        if(!fade && alpha > 1)
        {
            fade = true;
            alpha = 1;
        }
    }
}
