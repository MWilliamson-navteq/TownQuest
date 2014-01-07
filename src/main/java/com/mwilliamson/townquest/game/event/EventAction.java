package com.mwilliamson.townquest.game.event;

public interface EventAction
{
    public void onSourceAction(Interactable source) throws EventActionException;
    public void onTargetAction(Interactable target) throws EventActionException;
}
