package com.mwilliamson.townquest.app.controls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class RotateControl extends AbstractControl
{
    @Override
    protected void controlUpdate(float v)
    {
        getSpatial().rotate(0.5f * v, 2f * v, 0.5f * v);
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort)
    {
    }
}
