package com.mwilliamson.townquest;

import com.mwilliamson.townquest.game.GameLoop;
import org.lwjgl.LWJGLException;

public class Main
{
    public static void main(String[] args) throws LWJGLException {
        GameLoop gameLoop = new GameLoop();
        gameLoop.start();
    }
}
