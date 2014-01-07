package com.mwilliamson.townquest.input;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.mwilliamson.townquest.game.event.*;

public class InteractableMouse extends Interactable
{
    private Camera camera;
    private InputManager inputManager;
    private Node collisionTarget;

    public InteractableMouse(Camera camera, InputManager inputManager, Node collisionTarget)
    {
        this.camera = camera;
        this.inputManager = inputManager;
        this.collisionTarget = collisionTarget;

        setupMouseInput();
    }

    public void setupMouseInput()
    {
        InputHandler.addMouseListener(new MouseListener()
        {
            @Override
            public void onMove(int endX, int endY)
            {
            }

            @Override
            public void onLeftClickDown()
            {
                for (CollisionResult collisionResults : checkMouseCollision(collisionTarget))
                {
                    InteractableGeometry interactableGeometry = EventManager.getInteractable(collisionResults.getGeometry());
                    /*broadcastEvent(interactableGeometry, new EventAction()
                    {
                        @Override
                        public void onSourceAction(Interactable source) throws EventActionException
                        {
                        }

                        @Override
                        public void onTargetAction(Interactable target) throws EventActionException
                        {

                        }
                    });*/
                    //collisionResults.getGeometry().setUserData("isSpinning", false);
                    interactableGeometry.getGeometry().setUserData("isSpinning", false);

                }
            }

            @Override
            public void onRightCLickDown()
            {
            }

            @Override
            public void onLeftClickUp()
            {
                for (CollisionResult collisionResults : checkMouseCollision(collisionTarget))
                {
                    //collisionResults.getGeometry().rotate(1.0f, 0, 0);
                    collisionResults.getGeometry().setUserData("isSpinning", true);
                }
            }

            @Override
            public void onRightClickUp()
            {
            }

            @Override
            public void onScrollUp()
            {
            }

            @Override
            public void onScrollDown()
            {
            }
        });
    }

    private CollisionResults checkMouseCollision(Node geometryCollection)
    {
        Vector3f mousePosition = camera.getWorldCoordinates(inputManager.getCursorPosition(), 0f);
        Vector3f direction = camera.getWorldCoordinates(inputManager.getCursorPosition(), 1f); // Add 1f to the Z projection to make sure it's "in front" of the camera
        direction.subtractLocal(mousePosition).normalizeLocal();
        Ray ray = new Ray(mousePosition, direction);
        CollisionResults results = new CollisionResults();

        geometryCollection.collideWith(ray, results);

        return results;
    }
}
