package com.mwilliamson.townquest.input;

public interface MouseListener
{
    public void onMove(int endX, int endY);
    public void onLeftClickDown();
    public void onRightCLickDown();
    public void onLeftClickUp();
    public void onRightClickUp();
    public void onScrollUp();
    public void onScrollDown();

}
