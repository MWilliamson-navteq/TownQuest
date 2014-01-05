package com.mwilliamson.townquest.game.world;

import com.mwilliamson.townquest.game.world.quest.OnQuestListListener;

public interface World
{
    public void requestQuestList(OnQuestListListener onQuestListListener);
}
