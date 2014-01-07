package com.mwilliamson.townquest.game.event;

import com.jme3.scene.Geometry;

import java.util.HashMap;

public class EventManager
{
    private static final HashMap<Geometry, InteractableGeometry> interactableGeometryMap = new HashMap<Geometry, InteractableGeometry>();
    //public static LinkedList<GameEvent> gameEvents = new LinkedList<GameEvent>();

    public EventManager()
    {

    }

    public static InteractableGeometry getInteractable(Geometry geometry)
    {
        return interactableGeometryMap.get(geometry);
    }

    public static InteractableGeometry createInteractableGeomatry(Geometry geometry)
    {
        InteractableGeometry interactable = new InteractableGeometry(geometry);
        interactableGeometryMap.put(geometry, interactable);

        return interactable;
    }

    public static void broadcastEvent(GameEvent gameEvent)
    {
        // Probbably add an event queue here
        handleEvent(gameEvent);
    }

    private static void handleEvent(GameEvent gameEvent)
    {
        EventAction action = gameEvent.getAction();
        try
        {
            action.onSourceAction(gameEvent.getSource());
            action.onTargetAction(gameEvent.getTarget());
        }
        catch (EventActionException e)
        {
            e.printStackTrace();
        }

    }


}
