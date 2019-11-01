package norbertostudios.engine.main;////

import norbertostudios.engine.core.GameBase;
import norbertostudios.engine.graphics.Assets;
import norbertostudios.engine.graphics.Display;

////    Created     10/24/19, 8:01 AM
////    By:         Norberto Studios
////    
public class RunGame extends GameBase {

    private Display displayIt;

    @Override
    protected void initialize() {
        Assets.init();
        displayIt = new Display();

    }

    // update
    @Override
    protected void gameLoop() {
        displayIt.rendering();
    }
}
