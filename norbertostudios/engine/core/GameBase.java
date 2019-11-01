package norbertostudios.engine.core;

////    Created     10/24/19, 7:49 AM
////    By:         Norberto Studios
////    
public abstract class GameBase implements Runnable
{
    protected abstract void initialize();
    protected abstract void gameLoop();

    private volatile boolean running;
    private Thread thread;

    public GameBase()
    {
        initialize();
        start();
    }

    public void start()
    {
        thread = new Thread(this);
        thread.start();
        System.out.println("Stating Game Thread");
    }


    @Override
    public void run()
    {
        running = true;

        while (running)
        {
            gameLoop();

            sleep();
        }
        stop();
    }

    public void sleep()
    {
        try {
            thread.sleep(16);
        }catch (InterruptedException o){o.printStackTrace();}
    }

    public void stop()
    {
        try {
            thread.join();
            running = false;
        }catch (InterruptedException o){ o.printStackTrace();}
    }

}
