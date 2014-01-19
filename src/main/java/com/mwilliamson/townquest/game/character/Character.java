package com.mwilliamson.townquest.game.character;

import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.mwilliamson.townquest.app.controls.CameraOrbitControl;
import com.mwilliamson.townquest.app.controls.WalkControl;
import com.mwilliamson.townquest.input.InputHandler;
import com.mwilliamson.townquest.input.KeyPressListener;

public class Character
{
    private Geometry model;
    public Character(AssetManager assetManager, Node attachNode, Camera camera)
    {
        Box shape = new Box(1, 5, 1);
        model = new Geometry("Aelis", shape);

        //model.setLocalTranslation(new Vector3f(1,3,1));
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.randomColor());
        model.setMaterial(mat1);
        model.addControl(new WalkControl());
        model.addControl(new CameraOrbitControl(camera));

        setupInitialState();
        setupInputHandlers();

        attachNode.attachChild(model);
    }

    private void setupInitialState()
    {
        getSpatial().setUserData(WalkControl.MOVE_DIRECTION, WalkControl.DIRECTION_MOVE.NONE);
        getSpatial().setUserData(WalkControl.TURN_DIRECTION, WalkControl.DIRECTION_TURN.NONE);
        getSpatial().setUserData(WalkControl.HEADING, 0f);
    }

    private void setupInputHandlers()
    {
        InputHandler.addKeyPressListener(new KeyPressListener()
        {
            @Override
            public void onKeyDown(int key)
            {
                switch (key)
                {
                    case KeyInput.KEY_W:
                    {
                        move(WalkControl.DIRECTION_MOVE.FORWARD);
                        break;
                    }
                    case KeyInput.KEY_S:
                    {
                        move(WalkControl.DIRECTION_MOVE.BACKWARD);
                        break;
                    }
                    case KeyInput.KEY_A:
                    {
                        turn(WalkControl.DIRECTION_TURN.LEFT);
                        break;
                    }
                    case KeyInput.KEY_D:
                    {
                        turn(WalkControl.DIRECTION_TURN.RIGHT);
                        break;
                    }
                }
            }

            @Override
            public void onKeyUp(int key)
            {
                switch (key)
                {
                    case KeyInput.KEY_W:
                    {
                        move(WalkControl.DIRECTION_MOVE.NONE);
                        break;
                    }
                    case KeyInput.KEY_S:
                    {
                        move(WalkControl.DIRECTION_MOVE.NONE);
                        break;
                    }
                    case KeyInput.KEY_A:
                    {
                        turn(WalkControl.DIRECTION_TURN.NONE);
                        break;
                    }
                    case KeyInput.KEY_D:
                    {
                        turn(WalkControl.DIRECTION_TURN.NONE);
                        break;
                    }
                }
            }
        });
    }

    private void move(WalkControl.DIRECTION_MOVE moveDirection)
    {
        getSpatial().setUserData(WalkControl.MOVE_DIRECTION, moveDirection);
    }

    public Spatial getSpatial()
    {
        return model;
    }

    public void turn(WalkControl.DIRECTION_TURN direction)
    {
        getSpatial().setUserData(WalkControl.TURN_DIRECTION, direction);
    }
}
