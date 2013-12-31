package com.mwilliamson.townquest.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.LinkedList;

public class InputHandler
{
    private static final int BUTTON_LEFT = 0;
    private static final int BUTTON_RIGHT = 1;

    private static LinkedList<MouseListener> mouseListeners = new LinkedList<MouseListener>();
    private static LinkedList<KeyPressListener> keyPressListeners = new LinkedList<KeyPressListener>();

    private int oldMouseX = 0;
    private int oldMouseY = 0;
    private boolean oldLeftDown = false;
    private boolean oldRightDown = false;

    public void handleInput()
    {
        handleKeyboard();

        handleMouseMovement();
        handleMouseButtons();
        handleMouseScroll();
    }

    private void handleKeyboard()
    {
        while (Keyboard.next())
        {
            int key = Keyboard.getEventKey();
           for (KeyPressListener keyPressListener : keyPressListeners)
           {
               if (Keyboard.getEventKeyState())
                   keyPressListener.onKeyDown(key);
               else
                   keyPressListener.onKeyUp(key);
           }

        }
    }

    private void handleMouseScroll()
    {
        int delta = Mouse.getDWheel();
        if (delta != 0)
        {
            for (MouseListener listener : mouseListeners)
            {
                if (delta > 0)
                    listener.onScrollUp();
                else
                    listener.onScrollDown();
            }
        }
    }

    private void handleMouseButtons()
    {
        boolean isLeftDown = Mouse.isButtonDown(BUTTON_LEFT);
        boolean isRightDown = Mouse.isButtonDown(BUTTON_RIGHT);

        if (isLeftDown != oldLeftDown)
        {
            if (isLeftDown)
            {
                for (MouseListener listener : mouseListeners)
                {
                    listener.onLeftClickDown();
                }
            }
            else
            {
                for (MouseListener listener : mouseListeners)
                {
                    listener.onLeftClickUp();
                }
            }
        }

        if (isRightDown != oldRightDown)
        {
            if (isRightDown)
            {
                for (MouseListener listener : mouseListeners)
                {
                    listener.onRightCLickDown();
                }
            }
            else
            {
                for (MouseListener listener : mouseListeners)
                {
                    listener.onRightClickUp();
                }
            }
        }

        oldLeftDown = isLeftDown;
        oldRightDown = isRightDown;
    }

    private void handleMouseMovement()
    {
        int mouseX = Mouse.getX();
        int mouseY = Mouse.getY();
        if (mouseX != oldMouseX && mouseY != oldMouseY)
        {
            for (MouseListener listener : mouseListeners)
            {
                listener.onMove(mouseX, mouseY);
            }
            oldMouseX = mouseX;
            oldMouseY = mouseY;
        }
    }

    public static void addMouseListener(MouseListener mouseListener)
    {
        mouseListeners.add(mouseListener);
    }

    public static void removeMouseListener(MouseListener mouseListener)
    {
        mouseListeners.remove(mouseListener);
    }

    public static void addKeyPressListener(KeyPressListener keyPressListener)
    {
        keyPressListeners.add(keyPressListener);
    }

    public static void removeKeyPressListener(KeyPressListener keyPressListener)
    {
        keyPressListeners.remove(keyPressListener);
    }
}
