package com.mwilliamson.townquest.util;

import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector3f;

public class DisplayUtils
{
    private static DisplayMode currentDisplayMode;

    public static void setCurrentDisplayMode(DisplayMode displayMode)
    {
        currentDisplayMode = displayMode;
    }

    public static Vector3f convertScreenToOpenGLPixel(Point point)
    {
        Vector3f glPoint = new Vector3f();
        // ((n / x) * 2) - 1
        float x = (point.getX() / (currentDisplayMode.getWidth()) * 2.0f) - 1;
        float y = (point.getY() / (currentDisplayMode.getHeight()) * 2.0f) - 1;
        glPoint.set(x, y);

        return glPoint;
    }
}
