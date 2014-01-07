package com.mwilliamson.townquest.game.event;

public class Interactable
{
    public Interactable()
    {

    }

    protected final void broadcastEvent(Interactable target, EventAction action)
    {
        GameEvent event = new GameEvent(this, target, null, action);
        EventManager.broadcastEvent(event);
    }
}
