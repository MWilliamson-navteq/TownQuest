package com.mwilliamson.townquest.server;

import com.mwilliamson.townquest.game.world.World;
import com.mwilliamson.townquest.game.world.quest.OnQuestListListener;
import com.mwilliamson.townquest.game.world.quest.Quest;

import java.util.LinkedList;

public class DummyServerWorld implements World
{
    @Override
    public void requestQuestList(OnQuestListListener onQuestListListener)
    {
        LinkedList<Quest> questList = new LinkedList<Quest>();
        questList.add(new Quest()
        {
        });

        onQuestListListener.onQuestList(questList);
    }
}
