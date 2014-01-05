package com.mwilliamson.townquest.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InputHandler
{
    private static final int BUTTON_LEFT = 0;
    private static final int BUTTON_RIGHT = 1;

    private static LinkedList<MouseListener> mouseListeners = new LinkedList<MouseListener>();
    private static LinkedList<KeyPressListener> keyPressListeners = new LinkedList<KeyPressListener>();

    public void setup(InputManager inputManager)
    {
        setupKeyBoard(inputManager);
        setupMouse(inputManager);

    }

    private void setupKeyBoard(InputManager inputManager)
    {
        inputManager.clearMappings();
        try
        {
            for (final Field f : KeyInput.class.getDeclaredFields())
            {
                final String keyName = f.getName();
                final int keyValue = f.getInt(KeyInput.class);
                inputManager.addMapping(keyName, new KeyTrigger(keyValue));
                inputManager.addListener(new ActionListener()
                {
                    @Override
                    public void onAction(String name, boolean isDown, float tpf)
                    {
                        if (name.equals(keyName))
                        {
                            for (KeyPressListener keyPressListener : keyPressListeners)
                            {
                                if (isDown)
                                    keyPressListener.onKeyDown(keyValue);
                                else
                                    keyPressListener.onKeyUp(keyValue);
                            }
                        }
                    }
                }, keyName);
            }
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Error setting up key input!");
        }
    }

    private void setupMouse(InputManager inputManager)
    {
        try
        {
            for (final Field f : MouseInput.class.getDeclaredFields())
            {
                final String keyName = f.getName();
                final int keyValue = f.getInt(MouseInput.class);
                // Hacky, but there isn't a good solution since the axis and button movements are mixed together
                if (keyName.contains("BUTTON"))
                {
                    inputManager.addMapping(keyName, new MouseButtonTrigger(keyValue));
                    inputManager.addListener(new ActionListener()
                    {
                        @Override
                        public void onAction(String name, boolean isDown, float tpf)
                        {
                            if (name.equals(keyName))
                            {
                                handleMouseClicks(keyValue, isDown);
                            }
                        }
                    }, keyName);
                }
                else
                {
                    // Figure out mouse movement
                    /*inputManager.addMapping(keyName, new MouseAxisTrigger(keyValue, true));
                    inputManager.addListener(new AnalogListener()
                    {
                        @Override
                        public void onAnalog(String s, float v, float v2)
                        {
                            mouseListeners.get().
                        }
                    })*/
                }


            }
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException("Error setting up key input!");
        }
    }

    private void handleMouseMovement()
    {

    }

    private void handleMouseClicks(int button, boolean isDown)
    {
        switch (button)
        {
            case MouseInput.BUTTON_LEFT:
            {
                for (MouseListener mouseListener : mouseListeners)
                {
                    if (isDown)
                        mouseListener.onLeftClickDown();
                    else
                        mouseListener.onLeftClickUp();
                }
                break;
            }
            case MouseInput.BUTTON_RIGHT:
            {
                for (MouseListener mouseListener : mouseListeners)
                {
                    if (isDown)
                        mouseListener.onRightCLickDown();
                    else
                        mouseListener.onRightClickUp();
                }
                break;
            }
            case MouseInput.BUTTON_MIDDLE:
            {
                break;
            }
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
