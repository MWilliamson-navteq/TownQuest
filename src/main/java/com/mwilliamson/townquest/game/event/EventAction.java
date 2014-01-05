package com.mwilliamson.townquest.game.event;

public interface EventAction
{
    public void onSourceAction(Interactable source);
    public void onTargetAction(Interactable target);
}
