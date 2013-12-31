package com.mwilliamson.townquest.game;

import com.mwilliamson.townquest.util.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameLoop
{
    private volatile boolean isRunning;

    public GameLoop()
    {
        isRunning =  true;
    }

    public void start() throws LWJGLException
    {
        Display.setDisplayMode(new DisplayMode(800, 640));
        Display.create();

        try
        {
            long startTime = System.currentTimeMillis();
            while (isRunning)
            {
                if (startTime + (5 * 1000) < System.currentTimeMillis())
                    isRunning = false;
            }
        }
        finally
        {
            Log.log("Destroying screen");
            Display.destroy();
        }
    }

    public void stop()
    {
        isRunning = false;
    }
}
