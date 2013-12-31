package com.mwilliamson.townquest.game;

import com.mwilliamson.townquest.input.InputHandler;
import com.mwilliamson.townquest.input.KeyPressListener;
import com.mwilliamson.townquest.input.MouseListener;
import com.mwilliamson.townquest.rendering.Renderer;
import com.mwilliamson.townquest.util.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameLoop
{
    private volatile boolean isRunning;
    private final InputHandler inputHandler;
    private final Renderer renderer;

    public GameLoop()
    {
        isRunning =  true;
        inputHandler = new InputHandler();
        renderer = new Renderer();
    }

    public void start() throws LWJGLException
    {
        DisplayMode defaultDisplay = Display.getDisplayMode();
        Display.setDisplayMode(new DisplayMode(400, 320));
        //Display.setFullscreen(true);
        Display.create();

        InputHandler.addMouseListener(new DebugMouseListener());
        InputHandler.addKeyPressListener(new KillWindowListener());

        try
        {
            renderer.setup(defaultDisplay.getWidth(), defaultDisplay.getHeight());
            while (isRunning)
            {
                if (!Display.isCloseRequested())
                {
                    renderer.draw();
                    Display.update();
                    inputHandler.handleInput();
                    Display.sync(60);
                }
                else
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

    private class KillWindowListener implements KeyPressListener
    {

        @Override
        public void onKeyDown(int key)
        {
        }

        @Override
        public void onKeyUp(int key)
        {
            if (key == Keyboard.KEY_ESCAPE)
                isRunning = false;
        }
    }

    private class DebugMouseListener implements MouseListener
    {

        @Override
        public void onMove(int endX, int endY)
        {
            Log.log("Moved to: (" + endX + ", " + endY + ")");
        }

        @Override
        public void onLeftClickDown()
        {
            Log.log("Left Down");
        }

        @Override
        public void onRightCLickDown()
        {
            Log.log("Right Down");
        }

        @Override
        public void onLeftClickUp()
        {
            Log.log("Left Up");
        }

        @Override
        public void onRightClickUp()
        {
            Log.log("Right Down");
        }

        @Override
        public void onScrollUp()
        {
            Log.log("Scroll Up");
        }

        @Override
        public void onScrollDown()
        {
            Log.log("Scroll Down");
        }
    }
}
