package com.mwilliamson.townquest.game.event;

import com.jme3.scene.Geometry;

public class InteractableGeometry extends Interactable
{
    private Geometry geometry;

    public InteractableGeometry(Geometry geometry)
    {
        this.geometry = geometry;
    }

    public Geometry getGeometry()
    {
        return geometry;
    }
}
