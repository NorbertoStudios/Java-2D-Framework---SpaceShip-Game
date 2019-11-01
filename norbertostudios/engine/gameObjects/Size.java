package norbertostudios.engine.gameObjects;////

import norbertostudios.engine.graphics.Assets;

import java.awt.image.BufferedImage;

////    Created     10/28/19, 8:40 AM
////    By:         Norberto Studios
////    
public enum Size
{
    BIG(2, Assets.meds), MED(2, Assets.smalls), SMALL(2, Assets.tinies), TINY(0, null);

    public  int quantity;

    public BufferedImage[] textures;

    private  Size(int quantity, BufferedImage[] textures)
    {
        this.quantity = quantity;
        this.textures = textures;
    }

}
