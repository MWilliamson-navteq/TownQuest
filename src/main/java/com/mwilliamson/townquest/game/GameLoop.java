package com.mwilliamson.townquest.game;

import com.mwilliamson.townquest.input.InputHandler;
import com.mwilliamson.townquest.input.KeyPressListener;
import com.mwilliamson.townquest.input.MouseListener;
import com.mwilliamson.townquest.util.Log;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

public class GameLoop
{
    private volatile boolean isRunning;
    private final InputHandler inputHandler;

    public GameLoop()
    {
        isRunning =  true;
        inputHandler = new InputHandler();
    }

    public void start() throws LWJGLException
    {
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
        DisplayMode defaultDisplay = Display.getDisplayMode();
        Display.setDisplayMode(defaultDisplay);
        //Display.setFullscreen(true);
        Display.create(pixelFormat, contextAtrributes);
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));

       // InputHandler.addMouseListener(new DebugMouseListener());
        InputHandler.addKeyPressListener(new KillWindowListener());

        try
        {
            while (isRunning)
            {
                if (!Display.isCloseRequested())
                {
                    Display.sync(60);
                    Display.update();
                    //inputHandler.handleInput();

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
