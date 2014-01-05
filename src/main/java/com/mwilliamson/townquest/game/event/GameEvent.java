package com.mwilliamson.townquest.game.event;

public class GameEvent
{
    private Interactable target;
    private EventType eventType;
    private Interactable source;
    private EventAction action;

    public GameEvent(Interactable target, EventType eventType, Interactable source, EventAction action)
    {
        this.target = target;
        this.eventType = eventType;
        this.source = source;
        this.action = action;
    }

    public Interactable getTarget()
    {
        return target;
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Interactable getSource()
    {
        return source;
    }

    public EventAction getAction()
    {
        return action;
    }
}
