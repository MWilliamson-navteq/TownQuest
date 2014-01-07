package com.mwilliamson.townquest.app.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

public class RunningAppState extends AbstractAppState
{
    private SimpleApplication application;

    @Override
    public void initialize(AppStateManager stateManager, Application application)
    {
        super.initialize(stateManager, application);
        this.application = (SimpleApplication) application;          // cast to a more specific class

        // init stuff that is independent of whether state is PAUSED or RUNNING
    }

    @Override
    public void cleanup()
    {
        super.cleanup();
        // unregister all my listeners, detach all my nodes, etc...
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled)
        {
            // init stuff that is in use while this state is RUNNING
        }
        else
        {
        }
    }

    // Note that update is only called while the state is both attached and enabled.
    @Override
    public void update(float tpf)
    {
        // do the following while game is RUNNING
    }
}
