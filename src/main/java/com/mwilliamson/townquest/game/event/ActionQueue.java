package com.mwilliamson.townquest.game.event;

import java.util.LinkedList;

public class ActionQueue
{
    private static final LinkedList<ActionWrapper> actionQueue = new LinkedList<ActionWrapper>();
    private static final LinkedList<ActionListener> actionListeners = new LinkedList<ActionListener>();

    public static void addActionListener(ActionListener actionListener)
    {
        actionListeners.add(actionListener);
    }

    public static void addAction(Action action, Interactable source)
    {

        actionQueue.add(new ActionWrapper(action, source));

        // This is likely to end up handled on the next frame, but we'll see how this does for now
        handleActions();
    }

    // If handled as part of the game thread, make this public void handleActions()
    private static void handleActions()
    {
        for (ActionWrapper actionWrapper : actionQueue)
        {
            for (ActionListener listener : actionListeners)
            {
                //listener.onAction(actionWrapper.getAction(), actionWrapper.getSource());
            }
        }

        actionQueue.clear();
    }

    private static class ActionWrapper
    {
        private Action action;
        private Interactable source;

        private ActionWrapper(Action action, Interactable source)
        {
            this.action = action;
            this.source = source;
        }

        private Action getAction()
        {
            return action;
        }

        private Object getSource()
        {
            return source;
        }
    }

}
