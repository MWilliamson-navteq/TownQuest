package com.mwilliamson.townquest.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Log
{
    public static void log(String info)
    {
        System.out.println(info);
    }

    public static void glError()
    {
        log(GLU.gluErrorString(GL11.glGetError()));
    }
}
