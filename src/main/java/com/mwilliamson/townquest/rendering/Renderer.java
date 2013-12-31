package com.mwilliamson.townquest.rendering;

import static org.lwjgl.opengl.GL11.*;

public class Renderer
{
    public void setup(int width, int height)
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, 0, height, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    public void draw()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glColor3f(0.5f,0.5f,1.0f);
        glBegin(GL_QUADS);
        glVertex2f(100,100);
        glVertex2f(100+200,100);
        glVertex2f(100+200,100+200);
        glVertex2f(100,100+200);
        glEnd();
    }
}
